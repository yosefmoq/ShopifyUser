package com.app.shopifyuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopifyuser.R;
import com.app.shopifyuser.model.CartItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.MyViewHolder> {

    Context context;
    ArrayList<CartItem> cartItems;
    private final CollectionReference menuRef;
    //    private DocumentReference userRef;
    private final RemoveCartItemListener removeListener;

    public interface RemoveCartItemListener {
        Task<Void> removeCartItem(int itemId, int position);
    }

    public DeliveryAdapter(Context context, ArrayList<CartItem> cartItems, RemoveCartItemListener removeListener) {
        this.context = context;
        this.cartItems = cartItems;
        menuRef = FirebaseFirestore.getInstance().collection("menus");
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);

    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView itemIv, removeIv;
        private final TextView priceTv, nameTv, quantityTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIv = itemView.findViewById(R.id.itemIv);
            priceTv = itemView.findViewById(R.id.priceTv);
            nameTv = itemView.findViewById(R.id.nameTv);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            removeIv = itemView.findViewById(R.id.removeIv);
        }

        private void bind(CartItem cartItem) {

            quantityTv.setText("Quantity: " + cartItem.getQuantity());
            if (cartItem.getName() == null || cartItem.getName().isEmpty()) {
                getMenuItemInfo(cartItem.getId(), getAdapterPosition(), this);
            }

            removeIv.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == removeIv.getId()) {

                removeIv.setClickable(false);

                removeListener.removeCartItem(cartItems.get(getAdapterPosition()).getId(),
                        getAdapterPosition()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        removeIv.setClickable(true);
                        Toast.makeText(context, "There was an error while trying to" +
                                        " remove an item from your car! Please try again",
                                Toast.LENGTH_LONG).show();
                    }
                });


            } else {

//                context.startActivity(new Intent(context, OrderActivity.class)
//                        .putExtra("position", position));


            }

        }
    }


    private void getMenuItemInfo(int id, int pos, MyViewHolder holder) {

        menuRef.document(String.valueOf(id)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {

                        final String imageUrl = snapshot.getString("image");
                        final double price = snapshot.getDouble("price");
                        final String name = snapshot.getString("name");

                        cartItems.get(pos).setImageUrl(imageUrl);
                        cartItems.get(pos).setName(name);
                        cartItems.get(pos).setPrice(price);

                        holder.nameTv.setText("Name: " + name);
                        holder.priceTv.setText("Price: " + price + "$");
                        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.itemIv);

                    }
                });

    }

}
