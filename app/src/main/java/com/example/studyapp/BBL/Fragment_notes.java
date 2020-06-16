package com.example.studyapp.BBL;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.studyapp.DAL.Dao;
import com.example.studyapp.UI.NotesAdapter;
import com.example.studyapp.R;
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
    private ArrayList<Notes> mNotes = new ArrayList<>();
    private static final int REUEST_CODDE = 0;
    private View view;
    private DialogFragment_AddNotes dialogFragment_addNotes;
    private DialoFragment_startCount dialoFragmentStartCount ;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager ;
    private NotesAdapter adapter;
    private int UpdatePos ;
    public Fragment_notes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if( !(mNotes.isEmpty()) ){
            Notes temp = mNotes.get(UpdatePos);
            Dao dao = new Dao(getContext());
            temp.setHaveFinishMinutes(dao.GetProgress(temp.getNotesContent()));//
            mNotes.set(UpdatePos,temp);
            adapter.notifyItemChanged(UpdatePos);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {//这个函数在创建视图时调用，点击事件等方法不放在这里，这里用于加载控件（应该）
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_notes, container, false);
        btn_add = view.findViewById(R.id.add_FloatActBtn);
        btn_add.setOnClickListener(this);
        dialogFragment_addNotes = new DialogFragment_AddNotes();

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
        Dao dao = new Dao(getContext());
        mNotes = dao.getNoteData();
        adapter = new NotesAdapter(mNotes);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new NotesAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position, NotesAdapter.ViewName viewName) {//弹出学习时间窗口，输入后跳转至计时器
                //Toast.makeText(getActivity(),"ttttt",Toast.LENGTH_SHORT).show();
                Notes temp = mNotes.get(position);
                String noteContent = temp.getNotesContent();
                if (viewName.equals(NotesAdapter.ViewName.START)){
                    UpdatePos = position;
                    dialoFragmentStartCount = DialoFragment_startCount.newIntance(noteContent);//传入被点击的note在list（mNotes）中的序号
                    dialoFragmentStartCount.setTargetFragment(Fragment_notes.this,0);
                    dialoFragmentStartCount.show(getFragmentManager(),"StartDialogFragment");
                }else if (viewName.equals(NotesAdapter.ViewName.DELETE)){
                    //Toast.makeText(getActivity(),"ttttt",Toast.LENGTH_SHORT).show();
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, mNotes.size() - position);
                    mNotes.remove(position);
                    dao.DeleteNote(noteContent);
                    //
                }
            }
        });
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
            //Toast.makeText(getActivity(), content+" "+type+" "+finishDate[2]+" "+totalTime+" "+unitOfTime+" ", Toast.LENGTH_LONG).show();
            float i = Float.parseFloat(totalTime);
            Notes tNotes= new Notes();
            tNotes.setNotesContent(content);
            tNotes.setType(type);
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
