import mysql.connector
import serial;
from time import sleep
import datetime

#Подключение к базе данных
cnx = mysql.connector.connect(user='TestUser', password='TestUserPass', host='10.40.0.16', database='TemperatureDB');

#Настройка Com порта
ser = serial.Serial('COM5',4800);

#Класс сенсора
class Sensor:
    def __init__(self, id = None, address=None, value=None):
        self.id = id
        self.address = address
        self.value = value

#Класс Списка сенсоров
class SensorList:
    def __init__(self):
        self.List = [];

#Чтение списк сенсоров из базы
def ReadListSensors():
    sensorList = SensorList();
    x = cnx.cursor();
    x.execute("SELECT id, Address  FROM Sensors")
    rows = x.fetchall()

    for row in rows:
        sensorList.List.append(Sensor(id = row[0], address=row[1]));

    return sensorList;
    
#Чтение информации с сенсора
def ReadTemp(ser ):
    if(ser.is_open==False):
     ser.open()
   
    if(ser.is_open):
        line = ser.readline();
        value = GetDataFromRAW(line)        
        return value; 
    return None;

#Конвертирование данных в класс Sensor
def GetDataFromRAW(line):
    stringline = str(line);
    stringline = stringline.replace(';','').replace("b'","").replace("\\r\\n'","")
    splitline = stringline.split(':');
    
    return Sensor(address=splitline[0],value=float(splitline[1]))
    

def CheckAddressOnList(sensor):
    index = -1;
    for i in sensorList.List:
        index=index + 1;
        if (i.address == sensor.address):
            return index;
    return -1;

def CheckSensor(sensor):
    if(sensor != None):
        index = CheckAddressOnList(sensor);        
        if (index== -1):
            AddSensor(sensor.address)
        else:
            if(sensorList.List[index].value != round(sensor.value,1)):
                sensorList.List[index].value = round(sensor.value,1);
                AddTempData(sensorList.List[index]);
                print(round(sensor.value,1));

def AddSensor(address):
    x = cnx.cursor();
    x.execute("""INSERT INTO Sensors (Name,Address) VALUES(%s,%s)""",('NEW_'+address,address));  
    cnx.commit();
    sensorList = ReadListSensors();

def AddTempData(value):
    x = cnx.cursor();
    x.execute("""INSERT INTO SensorValue (Value,Sensor,DateTime) VALUES(%s,%s,%s)""",(value.value, value.id, datetime.datetime.now()));  
    cnx.commit();

sensorList = ReadListSensors();

while True:
    value = ReadTemp(ser);
    CheckSensor(value);
    sleep(1);
    
cnx.close()

