package nasuxjava.webnexus.controller.admin;

import nasuxjava.webnexus.entity.Distributor;
import nasuxjava.webnexus.services.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/Admin/Distributors")
@Controller
public class DistributorController {

    private static final String LAYOUT = "admin/template";
    private static final String PATH = "/admin/distributor";
    private static final String REDIRECTLIST = "redirect:/Admin/Distributors";

    @Autowired
    private DistributorService distributorService;
    @Autowired
    private HttpSession session;

    @Autowired
    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @GetMapping
    public String listDistributor(Model model) {
        List<Distributor> distributors = distributorService.getAllDistributors();
        model.addAttribute("urlForm", "Add");
        model.addAttribute("urlDelete", "Distributors");
        model.addAttribute("title", "List Distributors");
        model.addAttribute("distributor", new Distributor());
        model.addAttribute("distributors", distributors);
        model.addAttribute("content", PATH + "/list");
        return LAYOUT;
    }

    @GetMapping({ "/Add" })
    public String showAddDistributorForm(Model model) {
        model.addAttribute("title", "Add New Distributor");
        model.addAttribute("distributor", new Distributor());
        model.addAttribute("content", PATH + "/add.html");
        return LAYOUT;
    }

    @PostMapping({ "/Add" })
    public String distributorAdd(@Valid @ModelAttribute("distributor") Distributor distributor,
            BindingResult result,
            Model model) {

        Distributor existingDistributor = distributorService.getDistributorByName(distributor.getName());
        if (existingDistributor != null) {
            // result.rejectValue("name", "error.category", "Category already in use");
            session.setAttribute("message.error", "Distributor already in use");
            return REDIRECTLIST;
        }
        try {
            distributorService.saveDistributor(distributor);
            session.setAttribute("message.success", "New Distributor added successfully");
        } catch (Exception e) {
            session.setAttribute("message.error", "An error occurred while adding Distributor.");
        }

        return REDIRECTLIST;
    }

    @GetMapping("/Edit/{id}")
    public String showEditDistributorForm(@PathVariable Long id, Model model) {
        List<Distributor> categories = distributorService.getAllDistributors();
        model.addAttribute("title", "Edit Distributor");
        model.addAttribute("urlForm", "Edit/" + id);
        model.addAttribute("urlDelete", "Distributors");
        Distributor distributor = distributorService.getDistributorById(id);
        if (distributor == null) {
            session.setAttribute("message.error", "Distributors already in use");
            return REDIRECTLIST;
        }
        model.addAttribute("distributor", distributor);
        model.addAttribute("distributors", categories);
        model.addAttribute("content", PATH + "/list.html");
        return LAYOUT;
    }

    @PostMapping("/Edit/{id}")
    public String updateDistributor(@PathVariable Long id, @ModelAttribute Distributor distributor) {
        distributor.setId(id);
        try {
            distributorService.saveDistributor(distributor);
        } catch (Exception e) {
            session.setAttribute("message.error", "There was an error when editing Distributors.");
        }
        session.setAttribute("message.success", "Edit Distributors successfully");
        return REDIRECTLIST + "/Edit/" + id;
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Boolean> deleteDistributor(@PathVariable Long id) {
        try {
            distributorService.deleteDistributor(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}