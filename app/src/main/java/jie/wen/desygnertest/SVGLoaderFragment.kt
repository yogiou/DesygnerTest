package jie.wen.desygnertest

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jie.wen.desygnertest.databinding.FragmentSvgLoaderBinding
import jie.wen.desygnertest.component.SVGParser
import jie.wen.desygnertest.data.SVGImage
import jie.wen.desygnertest.data.SVGRect
import jie.wen.desygnertest.data.SVGText
import jie.wen.desygnertest.ui.component.SVGImageView
import jie.wen.desygnertest.ui.component.SVGRectView
import jie.wen.desygnertest.ui.component.SVGTextView
import java.lang.Exception


class SVGLoaderFragment : Fragment() {
    companion object {
        private const val TAG = "SVGLoaderFragment"
    }

    private var _binding: FragmentSvgLoaderBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSvgLoaderBinding.inflate(inflater, container, false)
        val layout = _binding?.root

        arguments?.getString(MainActivity.FILE_URI_PARAM)?.let { it ->
            val uri = Uri.parse(it)

            requireActivity().application.contentResolver.openInputStream(uri)?.let { stream ->

                val list = SVGParser().parse(stream)

                for (element in list) {
                    try {
                        this.context?.let { context ->
                            val view: View? = when (element) {
                                is SVGRect -> {
                                    SVGRectView(element, context)
                                }
                                is SVGImage -> {
                                    SVGImageView(element, context)
                                }
                                is SVGText -> {
                                    SVGTextView(element, context)
                                }
                                else -> {null}
                            }

                            view?.let {
                                layout?.addView(it)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    }
                }
                stream.close()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}