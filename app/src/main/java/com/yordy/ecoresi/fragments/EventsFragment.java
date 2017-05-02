package com.yordy.ecoresi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import com.yordy.ecoresi.BuildConfig;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.adapters.PostsAdapter;
import com.yordy.ecoresi.classes.Post;

public class EventsFragment extends Fragment {
    PostsAdapter adapter;
    @InjectView(R.id.posts)
    RecyclerView posts;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.inject((Object) this, rootView);
        ArrayList<Post> lista = new ArrayList();
        String url1 = "http://data.whicdn.com/images/90047661/large.jpg";
        String url4 = "http://surfbang.com/wp-content/uploads/2011/12/e4661f782a4c11e19896123138142014_7-610x610.jpg";
        String url3 = "http://40.media.tumblr.com/f2471f1b45637499b9ebd77d250d44eb/tumblr_moezawhlnO1s2nvs9o1_1280.jpg";
        String url2 = "http://surfbang.com/wp-content/uploads/2012/05/49e0a26c9b1111e181bd12313817987b_7-610x610.jpg";
        String url5 = "http://www.bestinstagram.com.br/wp-content/uploads/2014/02/fotos-de-surf-instagram-zak-noyle-02.jpg";
        String url_foto1 = "https://pbs.twimg.com/profile_images/572413804407816192/DZ2xqiBd.jpeg";
        String url_foto2 = "http://cdn.grindtv.com/wp-content/uploads/2012/08/round-4.png";
        String url_foto3 = "http://photos-a.ak.instagram.com/hphotos-ak-xfa1/t51.2885-19/11250104_848058701895928_600919306_a.jpg";
        lista.add(new Post(1, "Titulo", "Esta es la descripcion", "Evento en la playa", "345", url1, url_foto1));
        lista.add(new Post(1, "Titulo", "Foto de prueba", "Evento de prueba", "35", url2, url_foto2));
        lista.add(new Post(1, "Titulo", "Post de pruena", "Otro Evento", "3", url3, url_foto3));
        lista.add(new Post(1, "Titulo", "Esta n", "Olas y mas olas", "5", url4, url_foto1));
        lista.add(new Post(1, "Titulo", "Esta es la descripcion", "Evento en la playa", "345", url5, url_foto2));
        lista.add(new Post(1, "Titulo", "Foto de prueba", "Evento de prueba", "35", url1, url_foto3));
        lista.add(new Post(1, "Titulo", "Post de pruena", "Otro Evento", "3", url2, url_foto1));
        lista.add(new Post(1, "Titulo", "Esta n", "Olas y mas olas", "5", url3, url_foto2));
        lista.add(new Post(1, "Titulo", "Esta es la descripcion", "Evento en la playa", "345", url4, url_foto3));
        lista.add(new Post(1, "Titulo", "Foto de prueba", "Evento de prueba", "35", url5, url_foto1));
        lista.add(new Post(1, "Titulo", "Post de pruena", "Otro Evento", "3", url1, url_foto2));
        this.adapter = new PostsAdapter(getActivity(), lista);
        this.posts.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.posts.setAdapter(this.adapter);
        return rootView;
    }
}
