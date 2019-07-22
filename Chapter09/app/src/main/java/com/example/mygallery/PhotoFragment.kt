package com.example.mygallery


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*


private const val ARG_URI = "uri"

class PhotoFragment : Fragment() {
    private var uri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 2
        arguments?.let {
            uri = it.getString(ARG_URI)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // 3
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(uri).into(imageView)
    }

    // 1
    companion object {
        @JvmStatic
        fun newInstance(uri: String) =
                PhotoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_URI, uri)
                    }
                }
    }
}
