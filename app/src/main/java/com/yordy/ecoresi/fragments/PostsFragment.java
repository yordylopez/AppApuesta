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
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.adapters.PostsAdapter;
import com.yordy.ecoresi.classes.Post;

public class PostsFragment extends Fragment {
    PostsAdapter adapter;
    @InjectView(R.id.posts)
    RecyclerView posts;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.inject((Object) this, rootView);
        ArrayList<Post> lista = new ArrayList();
        String url1 = "http://1941125670.rsc.cdn77.org/blog/wp-content/uploads/2013/03/Screen-shot-2013-03-12-at-3.38.06-PM1-540x543.png";
        String url4 = "http://stwww.surfingmagazine.com/wp-content/uploads/2012/01/Shaka-Hand.jpg";
        String url3 = "http://1.bp.blogspot.com/-DKMOhLgQH9s/T8hWITu6n4I/AAAAAAAALKk/57zoPcLCbuA/s640/tumblr_lylde5LH2J1r3gmqfo1_500.png";
        String url2 = "http://41.media.tumblr.com/tumblr_mbx7igtxgi1reash6o2_1280.jpg";
        String url5 = "https://c2.staticflickr.com/8/7009/6804967971_245b539299.jpg";
        String url_foto1 = "http://s3-us-west-2.amazonaws.com/s.cdpn.io/6083/profile/profile-512_1.jpg";
        String url_foto2 = "http://1.bp.blogspot.com/-Alsq9Bnv67k/VA4OnZk13CI/AAAAAAAADug/WaConURUg8M/s1600/Round%2BProfile%2BPic.png";
        String url_foto3 = "http://static1.squarespace.com/static/525c54afe4b05530bc9987f5/t/52fa9c0ce4b0b6a53196454d/1392155662194/me_profile_pic_round.png";
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Habitación con baño, aire acondicionado, acceso a la cocina, estacionamiento", "Yordy Lopez", "345", url1, url_foto1));
        ArrayList<Post> arrayList = lista;
        arrayList.add(new Post(1, "Se alquila habitación en Maturin", "Video de prueba", "@mariap", "35", "http://casa-ensueno.com/wp-content/uploads/2015/01/Nicaragua-Surf-Aug-2011_720_24_utube.mp4?_=1", url_foto2));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Foto de prueba", "@mariap", "35", url2, url_foto2));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Post de pruena", "@otrouser", "3", url3, url_foto3));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Esta n", "@jhonnyx", "5", url4, url_foto1));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Esta es la descripcion", "@jhonnyx", "1", url5, url_foto2));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Esta ion", "@jhonnyx", "345", url1, url_foto3));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Esta es la descripcion", "@jhonnyx", "7", url2, url_foto1));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Esta ", "@jhonnyx", "9", url3, url_foto2));
        lista.add(new Post(1, "Se alquila habitación en Maturin", "Esta es la descripcion", "@jhonnyx", "12", url4, url_foto3));
        lista.add(new Post(1, "Se alquila habitación en MaturinSe alquila habitación en Maturin", "Esta es la descripcion", "@jhonnyx", "30", url5, url_foto1));
        lista.add(new Post(1, "Titulo", "Esta es la descripcion", "@jhonnyx", "89", url1, url_foto2));
        this.adapter = new PostsAdapter(getActivity(), lista);
        this.posts.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.posts.setAdapter(this.adapter);
        return rootView;
    }
}
