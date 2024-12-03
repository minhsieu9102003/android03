package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.view.ContextMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import vn.edu.hust.studentman.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentListAdapter: StudentListAdapter
    private val studentViewModel: StudentViewModel by activityViewModels()

    private var selectedStudentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No need to call setHasOptionsMenu(true) anymore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize the adapter
        studentListAdapter = StudentListAdapter(requireContext(), R.layout.list_item_student, studentViewModel.students)
        binding.listViewStudents.adapter = studentListAdapter

        // Register the ListView for Context Menu
        registerForContextMenu(binding.listViewStudents)

        // Setup MenuProvider
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Inflate your menu resource
                menuInflater.inflate(R.menu.options_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_add_new -> {
                        // Navigate to AddEditStudentFragment without passing a student
                        val action = StudentListFragmentDirections.actionStudentListFragmentToAddEditStudentFragment(
                            student = null,
                            position = -1
                        )
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // Rest of your fragment code (context menu handling, etc.)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
