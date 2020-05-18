package com.remy.firebasehello;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joneikholm.firebasehello.R;
import com.remy.firebasehello.adapter.MyRecycleViewAdapter;
import com.remy.firebasehello.model.Note;
import com.remy.firebasehello.storage.MemoryStorage;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String notes = "notes";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MyRecycleViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    EditText edt_title,edt_content;
    Button btn_post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_content = findViewById(R.id.edt_content);
        edt_title = findViewById(R.id.edt_title);
        btn_post = findViewById(R.id.btn_post);
        recyclerView = findViewById(R.id.recycler_view);

        startNoteListener();
       // deleteNote();
        editNote();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);
        startNoteListener();

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }
        });


    }




    private void editNote() {
        //edit
        DocumentReference docRef = db.collection(notes).document("Bp9zdrOb6jIzc8immqlW");
        Map<String,String> map = new HashMap<>();
        map.put("head", "changed head");
        map.put("body", "changed body");
        docRef.set(map);
    }

    private void deleteNote() {
        //delete:
        DocumentReference docRef = db.collection(notes).document("XkPbRbwY6xjhDbgAaURO");
        docRef.delete();
    }

    private void startNoteListener() {
        db.collection(notes).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                MemoryStorage.notes.clear();
                for (DocumentSnapshot snap: values.getDocuments()) {
                    Log.i("all", "read from FB" + snap.getId() + " " + snap.get("body").toString());
                    MemoryStorage.notes.add(new Note(snap.getId(),snap.get("head").toString(),snap.get("body").toString()));

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addNewNote() {
        DocumentReference docRef = db.collection(notes).document();
        Map<String,String> map = new HashMap<>();
        map.put("head", "new headline 2");
        map.put("body", "new body 2");
        docRef.set(map);
    }
}
