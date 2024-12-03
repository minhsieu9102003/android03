package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import vn.edu.hust.studentman.databinding.FragmentAddEditStudentBinding

class AddEditStudentFragment : Fragment() {

    private var _binding: FragmentAddEditStudentBinding? = null
    private val binding get() = _binding!!

    private val studentViewModel: StudentViewModel by activityViewModels()
    private val args: AddEditStudentFragmentArgs by navArgs()

    private var isEditMode: Boolean = false
    private var studentPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEditStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Check if editing an existing student
        if (args.student != null) {
            isEditMode = true
            studentPosition = args.position
            binding.editTextStudentName.setText(args.student?.studentName)
            binding.editTextStudentId.setText(args.student?.studentId)
            binding.buttonSave.text = "Save"
        } else {
            // Adding a new student
            binding.buttonSave.text = "Add"
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextStudentName.text.toString()
            val id = binding.editTextStudentId.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                val student = StudentModel(name, id)
                if (isEditMode && studentPosition != -1) {
                    studentViewModel.updateStudent(studentPosition, student)
                } else {
                    studentViewModel.addStudent(student)
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
