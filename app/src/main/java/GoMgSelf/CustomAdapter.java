package GoMgSelf;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gramy.HomeActivity;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.ShelfVO;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<ShelfVO> mList;
    private Context mContext;


    public class CustomViewHolder extends RecyclerView.ViewHolder  implements View.OnCreateContextMenuListener{

        protected TextView name;

        public CustomViewHolder(View view) {
            super(view);

            this.name = (TextView) view.findViewById(R.id.name_listitem);

            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

        }


        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001 :

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.shelf_editbox, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.shelf_button_dialog_submit);
                        final Button ButtonCancel = (Button) view.findViewById(R.id.shelf_button_dialog_submit_cancle);
                        //    DB에서 받아오는 값으로 교체
                        //    final EditText editTextID = (EditText) view.findViewById(R.id.shelf_edittext_dialog_id);
                        final EditText editTextName = (EditText) view.findViewById(R.id.shelf_edittext_dialog_name);


                        // editTextID.setText(mList.get(getAdapterPosition()).getList());
                        editTextName.setText(mList.get(getAdapterPosition()).getShelf_name());



                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // String strID = editTextID.getText().toString();
                                String strName = editTextName.getText().toString();

                                ShelfVO vo = new ShelfVO(strName);

                                mList.set(getAdapterPosition(), vo);

                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });

                        ButtonCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        break;

                    case 1002:

                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());

                        break;
                }
                return true;
            }


        };
    }

    public CustomAdapter(Context context, ArrayList<ShelfVO> list) {
        this.mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {

        viewHolder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewHolder.name.setGravity(Gravity.CENTER);

        viewHolder.name.setText(mList.get(position).getShelf_name());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(viewHolder.itemView.getContext(), HomeActivity.class);
                ContextCompat.startActivity(viewHolder.itemView.getContext(), intent, null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}