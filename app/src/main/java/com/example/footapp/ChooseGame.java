package com.example.footapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooseGame extends AppCompatActivity {

    private GamesList gamesList;
    private RecyclerView gamesListView;
    private GamesAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        Intent in = getIntent();
        gamesList = (GamesList) in.getSerializableExtra("GamesList");

        // setup recycler view
        gamesListView = findViewById(R.id.gamesList);
        gamesListView.setLayoutManager(new LinearLayoutManager(this));
        gamesAdapter = new GamesAdapter();
        gamesListView.setAdapter(gamesAdapter);

        gamesAdapter.notifyDataSetChanged();
    }

    private void deleteGameFromList(String gameName) {
        ArrayList<GameData> gameDataList = gamesList.getGameDataList();
        for (int i = 0; i < gamesList.getNumGames(); ++i) {
            if (gameDataList.get(i).getGameName().equals(gameName)) {
                gameDataList.remove(i);
                gamesList.setNumGames(gamesList.getNumGames() - 1);
            }
        }
    }


    private class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.PlayerHolder> {

        @Override
        public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ChooseGame.this).inflate(R.layout.listed_game, parent, false);
            return new PlayerHolder(v);
        }


        @Override
        public void onBindViewHolder(PlayerHolder holder, int position) {
            holder.bind(gamesList.getGameDataList().get(position));
        }

        @Override
        public int getItemCount() {
            return gamesList.getGameDataList().size();
        }

        public class PlayerHolder extends RecyclerView.ViewHolder {

            TextView listedGameName;
            ImageButton deleteGame;


            public PlayerHolder(View itemView) {
                super(itemView);
                listedGameName = itemView.findViewById(R.id.listedGameName);
                deleteGame = itemView.findViewById(R.id.deleteGame);
            }

            public void bind(final GameData gameData) {
                listedGameName.setText(gameData.getGameName());

                deleteGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteGameFromList(gameData.getGameName());
                    }
                });

                LinearLayout gameFrame = (LinearLayout)itemView.findViewById(R.id.gameFrame);
                gameFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
                        EditTeam.putExtra("Orig", 1);
                        EditTeam.putExtra("Game", gameData);
                        EditTeam.putExtra("GamesList",gamesList);
                        startActivity(EditTeam);
                    }
                });
            }
        }
    }
}
