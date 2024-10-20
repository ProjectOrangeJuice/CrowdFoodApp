package net.thejuggernaut.crowdfood.ui.scan;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TableTextWatch implements TextWatcher {

    ArrayList<EditText> items;
    ArrayList<EditText> values;
    EditText item;
    EditText val;
    TableRow r;
    Context c;
    TableLayout tbl;
    public TableTextWatch(ArrayList items, ArrayList values, TableRow r, EditText item, EditText val,
                          Context c, TableLayout tbl){
this.items = items;
this.values = values;
this.r = r;
this.item = item;
this.val = val;
this.c  = c;
this.tbl = tbl;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String t1 = item.getText().toString();
        String t2 = val.getText().toString();

        int foundEmpty = 0;
        //Check to see if there is an empty row
        for (int i = 0; i < items.size(); i++) {
            String t11 = items.get(i).getText().toString();
            String t22 = values.get(i).getText().toString();
            if (t11.equals("") && t22.equals("")) {
                foundEmpty += 1;
            }

        }
        if (t1.equals("") && t2.equals("")) {
            //We can remove this if needs be.
            if (foundEmpty > 1) {
                items.remove(item);
                values.remove(val);
                r.removeAllViews();
            }
        }else{
            if(foundEmpty == 0){
                TableRow curRow = new TableRow(c);
                EditText ent = new EditText(c);
                curRow.addView(ent);
                items.add(ent);
                EditText val = new EditText(c);
                values.add(val);
                ent.setHint("Fat");
                val.setHint("100g");
                curRow.addView(val);
                tbl.addView(curRow);
                TableTextWatch tbc = new TableTextWatch(items,values,curRow,ent,val,c,tbl);
                val.addTextChangedListener(tbc);
                ent.addTextChangedListener(tbc);
            }
        }
    }
}
