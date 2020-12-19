package com.example.musicroom.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.musicroom.R;
import com.google.firebase.auth.FirebaseAuth;

public class PermissionDialog extends AppCompatDialogFragment {
private RadioButton rbClient,rbAdmin;
private DialogListener listener;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view= inflater.inflate(R.layout.dialog_layout, null);
        rbClient=view.findViewById(R.id.client_b);
        rbAdmin=view.findViewById(R.id.admin_b);

        builder.setView(view).setTitle("Who are you?").setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseAuth mauth=FirebaseAuth.getInstance();
                if(rbClient.isChecked()){
                    String client=rbClient.getText().toString();
                    listener.applyText(client);
}

                if(rbAdmin.isChecked()){
                    String admin=rbAdmin.getText().toString();
                    listener.applyText(admin);
                }
            }
        });

        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener=(PermissionDialog.DialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must implement example dialog listenner");
        }
    }
    public interface DialogListener{
        void applyText(String permission);
    }

    /**
     * Remove dialog.
     */
   
}
