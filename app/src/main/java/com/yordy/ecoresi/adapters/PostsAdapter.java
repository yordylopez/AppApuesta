package com.yordy.ecoresi.adapters;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.malmstein.fenster.view.FensterVideoView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.classes.Post;
import com.yordy.ecoresi.views.CustomFontTextView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    protected static final String TAG;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Post> lista;

    /* renamed from: weneka.com.surfapp.adapters.PostsAdapter.1 */
    class C02191 implements OnPreparedListener {
        final /* synthetic */ ViewHolder val$viewHolder;

        /* renamed from: weneka.com.surfapp.adapters.PostsAdapter.1.1 */
        class C02181 implements OnClickListener {
            C02181() {
            }

            public void onClick(View view) {
                if (C02191.this.val$viewHolder.video.isPlaying()) {
                    C02191.this.val$viewHolder.play.setVisibility(0);
                    C02191.this.val$viewHolder.video.pause();
                    return;
                }
                C02191.this.val$viewHolder.play.setVisibility(8);
                C02191.this.val$viewHolder.video.start();
            }
        }

        C02191(ViewHolder viewHolder) {
            this.val$viewHolder = viewHolder;
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            this.val$viewHolder.imagen.setVisibility(8);
            this.val$viewHolder.video.setVisibility(0);
            this.val$viewHolder.play.setVisibility(8);
            this.val$viewHolder.video.setOnClickListener(new C02181());
        }
    }

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @InjectView(R.id.titulo)
        CustomFontTextView titulo;
        @InjectView(R.id.descripcion)
        CustomFontTextView descripcion;
        @InjectView(R.id.foto_user)
        ImageView foto_user;
        @InjectView(R.id.imagen)
        ImageView imagen;
        @InjectView(R.id.num_coments)
        CustomFontTextView num_coments;
        @InjectView(R.id.play)
        ImageView play;
        @InjectView(R.id.user)
        CustomFontTextView user;
        @InjectView(R.id.video)
        FensterVideoView video;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject((Object) this, itemView);
        }
    }

    static {
        TAG = PostsAdapter.class.getSimpleName();
    }

    public PostsAdapter(Activity activity, ArrayList<Post> lista) {
        this.inflater = LayoutInflater.from(activity);
        this.lista = lista;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_post, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Post item = (Post) this.lista.get(i);
        viewHolder.titulo.setText(item.getTitulo());
        viewHolder.descripcion.setText(item.getDescripcion());
        viewHolder.user.setText(item.getUser());
        viewHolder.num_coments.setText(item.getNumComents() + " Comentarios");
        if (item.getTipo() == 2) {
            viewHolder.imagen.setVisibility(0);
            viewHolder.imagen.setMaxHeight(230);
            Picasso.with(this.activity).load("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4").placeholder(this.activity.getResources().getDrawable(R.drawable.logo_login)).error(this.activity.getResources().getDrawable(R.drawable.logo_login)).into(viewHolder.imagen);
            viewHolder.video.setVideo(item.getUrl_image(), 3);
            viewHolder.video.start();
            viewHolder.video.setOnPreparedListener(new C02191(viewHolder));
        } else {
            viewHolder.imagen.setMaxHeight(260);
            viewHolder.video.setVisibility(8);
            viewHolder.play.setVisibility(8);
            viewHolder.imagen.setVisibility(0);
            Picasso.with(this.activity).load(item.getUrl_image()).placeholder(this.activity.getResources().getDrawable(R.drawable.logo_login)).error(this.activity.getResources().getDrawable(R.drawable.logo_login)).into(viewHolder.imagen);
        }
        Picasso.with(this.activity).load(item.getUrl_foto()).placeholder(this.activity.getResources().getDrawable(R.drawable.logo_login)).error(this.activity.getResources().getDrawable(R.drawable.logo_login)).into(viewHolder.foto_user);
        viewHolder.itemView.setTag(item);
    }

    public int getItemCount() {
        return this.lista.size();
    }
}
