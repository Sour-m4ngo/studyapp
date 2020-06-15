package com.example.studyapp.UI;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.studyapp.BBL.Notes;
import com.example.studyapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Notes> mNotesList;
    protected static final int REUEST_CODE = 1;
    private OnRecyclerViewItemClickListener mOnItemClickListener ;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /** item里面有多个控件可以点击 */
    public enum ViewName {
        START,
        DELETE
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position,ViewName viewName);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView NotesContent;
        TextView NotesType;
        TextView NotesProgress;//进度说明
        Button BtnStartTomato;
        Button BtnDeleteTomato;
        RoundCornerProgressBar NotePorgressBar;//进度条

        int i =0;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BtnStartTomato = itemView.findViewById(R.id.btn_notes_start);
            NotesContent = itemView.findViewById(R.id.tv_notes_content);
            NotesType = itemView.findViewById(R.id.tv_notes_type);//这里写点击事件
            NotePorgressBar = itemView.findViewById(R.id.progress);
            NotesProgress = itemView.findViewById(R.id.NoteProgress);
            BtnDeleteTomato = itemView.findViewById(R.id.btn_notes_delete);
            BtnStartTomato.setOnClickListener(this);
            BtnDeleteTomato.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
    public NotesAdapter (List<Notes> notesList){//构造函数，用于传入需要展示的数据。
        mNotesList = notesList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {//用于创建ViewHolder实例
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {//对RecyclerView子项的数据进行复制，在子项被滚动到屏幕内的时候执行
        Notes notes = mNotesList.get(position);//position为当前子项序号。
        viewHolder.NotesContent.setText(notes.getNotesContent());
        viewHolder.NotesType.setText(notes.getType());
        viewHolder.NotePorgressBar.setProgressColor(Color.parseColor("#40E0D0"));
        viewHolder.NotePorgressBar.setProgressBackgroundColor(Color.parseColor("#D3D3D3"));
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        float totaltime = notes.getTotalTime();
        String TimeUnit = notes.getUnitOfTime();
        if (TimeUnit.equals("小时")){
            totaltime = totaltime * 60;
        }
        float percent = notes.getHaveFinishMinutes() / totaltime;
        float curProgressBar = percent * totaltime;
        String curProgressBarInString = decimalFormat.format(curProgressBar);
        float RealProgress = Float.parseFloat(curProgressBarInString);
        viewHolder.NotePorgressBar.setMax(totaltime);
        viewHolder.NotePorgressBar.setProgress(RealProgress);

        if(notes.getType().equals("目标")){
            //notes.getFinishDate()[1]=notes.getFinishDate()[1]+1;
            int month =notes.getFinishDate()[1];
            if(notes.getUnitOfTime().equals("小时")){
                viewHolder.NotesProgress.setText(notes.getFinishDate()[0]+"年"+month+"月"+notes.getFinishDate()[2]+"日"+"  "+decimalFormat.format(notes.getHaveFinishMinutes()/60)+"/"+notes.getTotalTime()+" "+notes.getUnitOfTime());

            }else {
                viewHolder.NotesProgress.setText(notes.getFinishDate()[0]+"年"+month+"月"+notes.getFinishDate()[2]+"日"+"  "+notes.getHaveFinishMinutes()+"/"+notes.getTotalTime()+" "+notes.getUnitOfTime());

            }
        }
        else{
            if(notes.getUnitOfTime().equals("小时")){
                viewHolder.NotesProgress.setText(notes.getWorkFrequency()+" "+decimalFormat.format(notes.getHaveFinishMinutes()/60)+"/"+notes.getTotalTime()+"  "+notes.getUnitOfTime());
            }
            else {
                viewHolder.NotesProgress.setText(notes.getWorkFrequency()+" "+notes.getHaveFinishMinutes()+"/"+notes.getTotalTime()+"  "+notes.getUnitOfTime());
            }
        }
        if (mOnItemClickListener != null) {
            viewHolder.BtnStartTomato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, position,ViewName.START);
                }
            });
            viewHolder.BtnDeleteTomato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, position,ViewName.DELETE);
                }
            });

        }
        //viewHolder.
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }


}
