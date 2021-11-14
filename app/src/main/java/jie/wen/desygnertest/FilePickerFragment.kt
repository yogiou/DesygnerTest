package jie.wen.desygnertest

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import jie.wen.desygnertest.MainActivity.Companion.FILE_URI_PARAM
import jie.wen.desygnertest.databinding.FragmentFilePickerBinding

class FilePickerFragment : Fragment() {
    companion object{
        const val OPEN_DOCUMENT_REQUEST_CODE = 2
    }

    private val allSupportedDocumentsTypesToExtensions = mapOf(
        "image/svg+xml" to ".svg",
        "image/svg+xml" to ".svgz"
    )
    private val supportedMimeTypes: Array<String> = allSupportedDocumentsTypesToExtensions.keys.toTypedArray()

    private var _binding: FragmentFilePickerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilePickerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val openDocumentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_MIME_TYPES, supportedMimeTypes)
            }

            startActivityForResult(openDocumentIntent, OPEN_DOCUMENT_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == OPEN_DOCUMENT_REQUEST_CODE) {
            data?.data?.let { contentUri ->
                val bundle = Bundle()
                bundle.putString(FILE_URI_PARAM, contentUri.toString())
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}