package myapp.user.example.com.sikika.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import myapp.user.example.com.sikika.Audio;
import myapp.user.example.com.sikika.Camera;
import myapp.user.example.com.sikika.Document;
import myapp.user.example.com.sikika.MainActivity;
import myapp.user.example.com.sikika.R;
import myapp.user.example.com.sikika.doc;

public class HomeFragment extends Fragment {

    private CardView c1;
    private CardView c2;
    private CardView c3;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        CardView c1 = v.findViewById(R.id.card1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(), rights.class);
                startActivity(new Intent(getActivity(), Audio.class));
            }
        });

        CardView c2 = v.findViewById(R.id.card2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(), rights.class);
                startActivity(new Intent(getActivity(), Camera.class));
            }
        });

        CardView c3 = v.findViewById(R.id.card3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(), rights.class);
                startActivity(new Intent(getActivity(),Document.class));
            }
        });


        return v;
    }
}

   //homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);