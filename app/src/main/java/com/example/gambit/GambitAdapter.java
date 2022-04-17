package com.example.gambit;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.example.gambit.example.FoodData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GambitAdapter extends RecyclerView.Adapter<GambitAdapter.ViewHolder> {

    private final List<FoodData> foodDataList;
    private SharedPreferences gambitInBasket;
    private SharedPreferences gambitMinus;
    private SharedPreferences gambitPlus;
    private SharedPreferences gambitUmi;
    private SharedPreferences gambitGoodUmi;
    private SharedPreferences gambitFavorites;

    public GambitAdapter(List<FoodData> foodDataList) {
        this.foodDataList = foodDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view=LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.second_main, parent, false );
        ViewHolder vh=new ViewHolder ( view );
        if (gambitInBasket == null) {
            gambitInBasket=parent.getContext ().getSharedPreferences ( "INBASKET", Context.MODE_PRIVATE );
        }
        if (gambitMinus == null) {
            gambitMinus=parent.getContext ().getSharedPreferences ( "MINUS", Context.MODE_PRIVATE );
        }
        if (gambitPlus == null) {
            gambitPlus=parent.getContext ().getSharedPreferences ( "PLUS", Context.MODE_PRIVATE );
        }
        if (gambitUmi == null) {
            gambitUmi=parent.getContext ().getSharedPreferences ( "UMI", Context.MODE_PRIVATE );
        }
        if (gambitGoodUmi == null) {
            gambitGoodUmi=parent.getContext ().getSharedPreferences ( "GOOD_UMI", Context.MODE_PRIVATE );
        }
        if (gambitFavorites == null) {
            gambitFavorites =parent.getContext ().getSharedPreferences ( "FAVORITES", Context.MODE_PRIVATE );
        }
        return vh;
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        holder.bind ( foodDataList.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return foodDataList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button PriceNow;
        private final Button btnMinus;
        private final Button btnPlus;
        private final Button basketGm;
        private final TextView textView;
        private final TextView textView2;
        private FoodData newFoodData;
        private final ImageView imageView;
        private final ImageView imageFavorites;
        private final SwipeLayout swipeLayout;
        boolean basket;
        boolean plus;
        boolean minus;
        boolean summa;
        boolean likeId;
        int number= 1;

        public ViewHolder( @NonNull View itemView ) {
            super ( itemView );
            PriceNow =itemView.findViewById ( R.id.btnOne );
            imageView =itemView.findViewById ( R.id.imageOne );
            btnMinus =itemView.findViewById ( R.id.btnMinus );
            textView =itemView.findViewById ( R.id.textOne );
            basketGm =itemView.findViewById ( R.id.btnTwo );
            imageFavorites =itemView.findViewById ( R.id.vurgan );
            btnPlus =itemView.findViewById ( R.id.btnPlus );
            textView2=itemView.findViewById ( R.id.textTwo );
            swipeLayout=itemView.findViewById ( R.id.swipe );

            btnBasketClick ();
            swipeLayoutForRecItem ();
        }

        public void btnBasketClick() {
            basketGm.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    if (gambitInBasket.getBoolean ( String.valueOf ( newFoodData.getId () ), false )) {
                        basketGm.setVisibility ( View.VISIBLE );
                        saveDataBtnNow ( String.valueOf ( newFoodData.getId () ), false );

                        btnMinus.setVisibility ( View.VISIBLE );
                        saveDataMinus ( String.valueOf ( newFoodData.getId () ), false );

                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( newFoodData.getId () ), false );

                        textView2.setVisibility ( View.VISIBLE );
                        saveDataTextUmi ( String.valueOf ( newFoodData.getId () ), false );
                    } else {
                        basketGm.setVisibility ( View.GONE );
                        saveDataBtnNow ( String.valueOf ( newFoodData.getId () ), true );

                        btnMinus.setVisibility ( View.VISIBLE );
                        saveDataMinus ( String.valueOf ( newFoodData.getId () ), true );

                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( newFoodData.getId () ), true );

                        textView2.setVisibility ( View.VISIBLE );
                        textView2.setText ( String.valueOf ( 1 ) );
                        saveDataTextUmi ( String.valueOf ( newFoodData.getId () ), true );

                    }
                }
            } );
        }
        public void bind( FoodData foodData) {
            newFoodData = foodData;
            textView2.setText ( String.valueOf ( number ) );
            basket=gambitInBasket.getBoolean ( String.valueOf ( newFoodData.getId () ), true );
            minus=gambitMinus.getBoolean ( String.valueOf ( newFoodData.getId () ), true );
            plus=gambitPlus.getBoolean ( String.valueOf ( newFoodData.getId () ), true );
            summa=gambitUmi.getBoolean ( String.valueOf ( newFoodData.getId () ), true );
            likeId= gambitFavorites.getBoolean ( String.valueOf ( newFoodData.getId () ), true );
            number=gambitGoodUmi.getInt ( String.valueOf ( newFoodData.getId () ), 1 );


            String imageUrl= foodData.getImage ();
            Picasso.with ( itemView.getContext () )
                    .load ( imageUrl )
                    .into (imageView);
            textView.setText ( foodData.getName () );
            PriceNow.setText ( String.valueOf ( foodData.getPrice () ) );

            if (gambitInBasket.getBoolean ( String.valueOf ( newFoodData.getId () ), false )) {
                basketGm.setVisibility ( View.GONE );
            } else {
                basketGm.setVisibility ( View.VISIBLE );
            }
            if (gambitMinus.getBoolean ( String.valueOf ( newFoodData.getId () ), false )) {
                btnPlus.setVisibility ( View.VISIBLE );
            } else {
                btnPlus.setVisibility ( View.GONE );
            }
            if (gambitPlus.getBoolean ( String.valueOf ( newFoodData.getId () ), false )) {
                btnMinus.setVisibility ( View.VISIBLE );
            } else {
                btnMinus.setVisibility ( View.GONE );
            }
            if (gambitUmi.getBoolean ( String.valueOf ( newFoodData.getId () ), false )) {
                textView2.setVisibility ( View.VISIBLE );
            } else {
                textView2.setVisibility ( View.GONE );
            }
            if (gambitFavorites.getBoolean ( String.valueOf ( newFoodData.getId () ), likeId )) {
                imageFavorites.setImageResource ( R.drawable.ic_dontlike);
            } else {
                imageFavorites.setImageResource ( R.drawable.ic_like );
            }
                   clickerMinus();
                   clickerPlus ();
        }
        public void saveDataMinus( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=gambitMinus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }
        public void saveDataPlus( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=gambitPlus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }
        public void saveDataBtnNow( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=gambitInBasket.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }
        public void saveDataTextUmi( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=gambitUmi.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }
        public void saveDataLikeSum( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor= gambitFavorites.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }
        public void saveDataUmiLike( String id, int dataToSave ) {
            SharedPreferences.Editor editor=gambitGoodUmi.edit ();
            editor.putInt ( id, dataToSave );
            editor.apply ();
        }




        public void swipeLayoutForRecItem() {
            swipeLayout.setShowMode ( SwipeLayout.ShowMode.PullOut );
            swipeLayout.addDrag ( SwipeLayout.DragEdge.Right, swipeLayout.findViewById ( R.id.hasbik ) );
            swipeLayout.addSwipeListener ( new SwipeLayout.SwipeListener () {
                @Override
                public void onStartOpen( SwipeLayout layout ) {
                }

                @Override
                public void onOpen( SwipeLayout layout ) {
                    if (likeId) {
                        imageFavorites.setImageResource ( R.drawable.ic_like );
                        likeId = false;
                    } else {
                        imageFavorites.setImageResource ( R.drawable.ic_dontlike);
                        likeId = true;
                    }
                    saveDataLikeSum ( String.valueOf ( newFoodData.getId () ), likeId );
                }

                @Override
                public void onStartClose( SwipeLayout layout ) {
                }
                @Override
                public void onClose( SwipeLayout layout ) {
                }
                @Override
                public void onUpdate( SwipeLayout layout, int leftOffset, int topOffset ) {
                }
                @Override
                public void onHandRelease( SwipeLayout layout, float xvel, float yvel ) {
                    layout.postDelayed ( new Runnable () {
                        @Override
                        public void run() {
                            layout.close ();
                        }
                    }, 200 );
                }
            } );
        }

        public void clickerPlus() {
            btnPlus.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    number=Integer.parseInt ( textView2.getText ().toString () ) + 1;
                    textView2.setText ( String.valueOf ( number ) );
                    saveDataUmiLike ( String.valueOf ( newFoodData.getId () ), number );
                }
            } );
        }

        public void clickerMinus() {
            btnMinus.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    if (Integer.parseInt ( textView2.getText ().toString () ) > 0) {
                        number=Integer.parseInt ( textView2.getText ().toString () ) - 1;
                        textView2.setText ( String.valueOf ( number ) );
                        saveDataUmiLike ( String.valueOf ( newFoodData.getId () ), number );

                        if (Integer.parseInt ( textView2.getText ().toString () ) == 0) {
                            btnMinus.setVisibility ( View.GONE );
                            btnPlus.setVisibility ( View.GONE );
                            textView2.setVisibility ( View.GONE );


                            number=1;

                            basketGm.setVisibility ( View.VISIBLE );
                            saveDataBtnNow ( String.valueOf ( newFoodData.getId () ), false );
                            saveDataMinus ( String.valueOf ( newFoodData.getId () ), false );
                            saveDataPlus ( String.valueOf ( newFoodData.getId () ), false );
                            saveDataUmiLike ( String.valueOf ( newFoodData.getId () ), number );


                        }
                    }
                }
            } );
        }
    }
}