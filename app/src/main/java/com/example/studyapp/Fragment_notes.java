package com.example.studyapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Fragment_notes extends Fragment implements View.OnClickListener{
    private FloatingActionButton btn_add;
    private ArrayList<Notes> mNotes = new ArrayList<Notes>();
    protected static final int REUEST_CODDE = 0;
    private View view;
    private View view_AlertDialog;
    private AlertDialog AlertDialog = null;
    private AlertDialog.Builder Builder_AlterDialog;
    private DialogFragment_AddNotes dialogFragment_addNotes;
    private DialoFragment_startCount dialoFragmentStartCount ;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager ;
    private NotesAdapter adapter;
    public Fragment_notes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {//这个函数在创建视图时调用，点击事件等方法不放在这里，这里用于加载控件（应该）
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_notes, container, false);
        btn_add = view.findViewById(R.id.add_FloatActBtn);
        btn_add.setOnClickListener(this);
        dialogFragment_addNotes = new DialogFragment_AddNotes();
        dialoFragmentStartCount = new DialoFragment_startCount();
       initView();
       initAdapter();
       //initData();
       return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {//这个在fragment所存在的活动oncreate方法执行完毕后被调用
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_FloatActBtn:
                dialogFragment_addNotes.setTargetFragment(Fragment_notes.this, REUEST_CODDE);
                dialogFragment_addNotes.show(getFragmentManager(), "NoticeDialogFragment");
                break;
            default:
                break;
        }
    }
    private void initView(){
        recyclerView= view.findViewById(R.id.recycler_view);
    }
    private void initAdapter (){
        adapter = new NotesAdapter(mNotes);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new NotesAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(),"ttttt",Toast.LENGTH_SHORT).show();
                dialoFragmentStartCount.setTargetFragment(Fragment_notes.this,0);
                dialoFragmentStartCount.show(getFragmentManager(),"StartDialogFragment");
            }
        });
    }
    private void initData(){
        for(int i = 0;i<5;i++){
            Notes tNotes= new Notes();
            tNotes.setNotesContent("内容测试数据"+i);
            tNotes.setType("类型测试"+i);
            mNotes.add(tNotes);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REUEST_CODDE){
            String content = data.getStringExtra(DialogFragment_AddNotes.CONTENT);
            String type= data.getStringExtra(DialogFragment_AddNotes.TYPE);
            int[]  finishDate = data.getIntArrayExtra(DialogFragment_AddNotes.FINISHDATE);
            String totalTime = data.getStringExtra(DialogFragment_AddNotes.TOTALTIME);
            String unitOfTime = data.getStringExtra(DialogFragment_AddNotes.UNITOFTIME);
            String workFrequency = data.getStringExtra(DialogFragment_AddNotes.WORKFREQUENCY);
            Toast.makeText(getActivity(), content+" "+type+" "+finishDate[2]+" "+totalTime+" "+unitOfTime+" ", Toast.LENGTH_LONG).show();
            Notes tNotes= new Notes();
            tNotes.setNotesContent(content);
            tNotes.setType(type);
            int i = Integer.parseInt(totalTime);
            tNotes.setTotalTime(i);
            tNotes.setWorkFrequency(workFrequency);
            tNotes.setUnitOfTime(unitOfTime);
            tNotes.setFinishDate(finishDate);
            Dao dao = new Dao(getContext());
            dao.SaveNoteData(tNotes);
            mNotes.add(tNotes);
        }
    }


}
