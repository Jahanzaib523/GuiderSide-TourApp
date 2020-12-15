package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.semester_project.smd_project.R;

public class Wallet_Fragment extends Fragment
{
    public Wallet_Fragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        getActivity().setTitle("Wallet");
        return root;
    }
}
