package com.example.tinderapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    int rightCount=0, leftCount=0;
    TextView rightSwipe, leftSwipe;
    Button btnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rightSwipe = findViewById(R.id.rightSwipe);
        leftSwipe = findViewById(R.id.leftSwipe);
        btnCount = findViewById(R.id.btnCount);

        rightSwipe.setVisibility(View.GONE);
        leftSwipe.setVisibility(View.GONE);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String res1 = "Number of Right Swipe = " + rightCount;
                String res2 = "Number of Left Swipe = " + leftCount;

                rightSwipe.setText(res1);
                leftSwipe.setText(res2);

                rightSwipe.setVisibility(View.VISIBLE);
                leftSwipe.setVisibility(View.VISIBLE);

            }
        });

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                rightSwipe.setVisibility(View.GONE);
                leftSwipe.setVisibility(View.GONE);
                if (direction == Direction.Right){
                    rightCount = rightCount + 1;
                    Toast.makeText(MainActivity.this, "Direction Right", Toast.LENGTH_SHORT).show();
                }

                if (direction == Direction.Left){
                    leftCount = leftCount+1;
                    Toast.makeText(MainActivity.this, "Direction Left", Toast.LENGTH_SHORT).show();
                }


                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.galgadot, "Gal Gadot", "34", "Israel"));
        items.add(new ItemModel(R.drawable.alexandra1, "Alexandra Daddario", "36", "New York City"));
        items.add(new ItemModel(R.drawable.kkk, "Karen Gillan", "27", "Jonggol"));
        items.add(new ItemModel(R.drawable.vijay, "Vijay Sethupathi", "46", "Chennai"));
        items.add(new ItemModel(R.drawable.kgfstory, "Yash", "35", "Chennai"));

        items.add(new ItemModel(R.drawable.rashmikamandanna, "rashmika-mandhan", "29", "India"));
        items.add(new ItemModel(R.drawable.mun, "Divyendu Sharma", "30", "Mumbai"));
        items.add(new ItemModel(R.drawable.robert, "Sukijah", "27", "Jonggol"));
        items.add(new ItemModel(R.drawable.sample4, "Markobar", "19", "Bandung"));
        items.add(new ItemModel(R.drawable.sample5, "Marmut", "25", "Hutan"));
        return items;
    }
}