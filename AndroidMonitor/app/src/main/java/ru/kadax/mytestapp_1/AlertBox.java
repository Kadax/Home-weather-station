package ru.kadax.mytestapp_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Kadax on 04.12.2017.
 */

public class AlertBox {

    Context context;

    AlertBox(Context _context){
        context=_context;
    }

    public void Show(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Сообщение")
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
