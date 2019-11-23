package com.example.mygallery


import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*


private const val ARG_URI = "uri"

class PhotoFragment : Fragment() {
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 2
        arguments?.getParcelable<Uri>(ARG_URI)?.let {
            uri = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // 3
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // use 함수는 자동으로 close 해 줌
        val descriptor = requireContext().contentResolver.openFileDescriptor(uri, "r")
        descriptor?.use {
            val bitmap = BitmapFactory.decodeFileDescriptor(descriptor.fileDescriptor)
            Glide.with(this).load(bitmap).into(imageView)
        }
    }

    // 1
    companion object {
        @JvmStatic
        fun newInstance(uri: Uri) =
                PhotoFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_URI, uri)
                    }
                }
    }
}
