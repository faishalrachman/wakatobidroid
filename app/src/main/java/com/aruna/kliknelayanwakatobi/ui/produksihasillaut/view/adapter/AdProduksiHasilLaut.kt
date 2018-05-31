package apri.aruna.com.apriapp.ui.fishlog.fishloglist.view

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.interfacecustom.OnClickRecyclerItem
import com.aruna.kliknelayanwakatobi.pojo.ModelTransaction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_produksi_hasil_laut.view.*

/**
 * Created by marzellaalfamega on 11/18/17.
 */
class AdProduksiHasilLaut(val listTransaction: MutableList<ModelTransaction>, val listener: OnClickRecyclerItem) :
        RecyclerView.Adapter<AdProduksiHasilLaut.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listener: OnClickRecyclerItem) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_produksi_hasil_laut, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelTransaction = listTransaction[position]

        holder.itemView.tvToolsRowProduksiHasilLaut.text = modelTransaction.pickup_tools
        if (modelTransaction.pickup_geoloc != null) {
            holder.itemView.tvLocationRowProduksiHasilLaut.text = "${modelTransaction.pickup_geoloc!!.latitude} , ${modelTransaction.pickup_geoloc!!.longitude}"
        }

        holder.itemView.tvPriceRowProduksiHasilLaut.text = "Rp ${modelTransaction.price}"

        if (!TextUtils.isEmpty(modelTransaction.photo)) {
            Picasso.with(holder.itemView.context).load(modelTransaction.photo).into(holder.itemView.ivRowProduksiHasilLaut)
        }

//        holder.bind(listener)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder?) {
        super.onViewAttachedToWindow(holder)
        //        if (lastPosition < holder.getAdapterPosition()) {
        //            lastPosition++;
        //            setAnimationIn(holder.parentRowMain);
        //        } else {
        //            lastPosition--;
        //        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        //        holder.parentRowMain.clearAnimation();
    }

    override fun getItemCount(): Int = listTransaction.size


    //    private void setAnimationIn(View viewToAnimate) {
    //        AlphaAnimation anim = new AlphaAnimation(0F, 1F);
    //        anim.setDuration(Constants.DEFAULT_ANIM_DURATION);
    //        viewToAnimate.startAnimation(anim);
    //    }

}