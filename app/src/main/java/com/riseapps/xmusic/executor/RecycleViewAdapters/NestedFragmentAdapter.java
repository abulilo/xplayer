package com.riseapps.xmusic.executor.RecycleViewAdapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.riseapps.xmusic.R;
import com.riseapps.xmusic.component.CustomAnimation;
import com.riseapps.xmusic.executor.PlaySongExec;
import com.riseapps.xmusic.model.Pojo.Song;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NestedFragmentAdapter extends RecyclerView.Adapter {

    private List<Song> songsList;
    Context c;

    private static final int AD_TYPE=0;
    private static final int NORMAL_TYPE=1;

    public NestedFragmentAdapter(Context context, List<Song> songs, RecyclerView recyclerView) {
        songsList = songs;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            c = context;


        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case AD_TYPE:
                View v1 = inflater.inflate(R.layout.nativ_express_ad_container, parent, false);
                vh=new AdViewHolder(v1);
                break;
            case NORMAL_TYPE:
                View v = inflater.inflate(R.layout.song_name_rows, parent, false);
                vh = new NestedSongViewHolder(v, c);
                break;
        }
        return vh;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NestedSongViewHolder) {
            Song song = (Song) songsList.get(position);

            String name = song.getName();
            String artist = song.getArtist();

            ((NestedSongViewHolder) holder).name.setText(name);

            ((NestedSongViewHolder) holder).artist.setText(artist);

            ((NestedSongViewHolder) holder).song = song;

        }
        else {
            ((AdViewHolder)holder).adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    ((AdViewHolder)holder).adView.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return songsList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if(songsList.get(position)==null)
            return AD_TYPE;

        return NORMAL_TYPE;
    }

    private class AdViewHolder extends RecyclerView.ViewHolder {

        NativeExpressAdView adView;
        AdViewHolder(View view) {
            super(view);
            adView = (NativeExpressAdView)view.findViewById(R.id.adView);
            adView.setVisibility(View.GONE);
            AdRequest request = new AdRequest.Builder()
                    . addTestDevice("1BB6AD3C4E832E63122601E2E4752AF4")
                    .build();
            adView.loadAd(request);
        }
    }
}
class NestedSongViewHolder extends RecyclerView.ViewHolder {

    TextView name,artist;
    private Context ctx;
    Song song;

    NestedSongViewHolder(View v, Context context) {
        super(v);
        this.ctx = context;

        name= (TextView) v.findViewById(R.id.name);
        artist= (TextView) v.findViewById(R.id.artist);
    }

}