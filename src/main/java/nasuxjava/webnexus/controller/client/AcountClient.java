package nasuxjava.webnexus.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@RequestMapping("/Acount")
@Controller
public class AcountClient {

    private static final String LAYOUT = "client/templateOther";
    private static final String PATH_ACOUNT = "/client/acount";

    @GetMapping("/Dashboard")
    public String viewDashboard(Model model) {
        model.addAttribute("content", PATH_ACOUNT + "/dashboard.html");
        return LAYOUT;
    }

    @GetMapping("/Address")
    public String viewAddress(Model model) {
        model.addAttribute("content", PATH_ACOUNT + "/address.html");
        return LAYOUT;
    }

    @GetMapping("/AddressEdit")
    public String viewAddressEdit(Model model) {
        model.addAttribute("content", PATH_ACOUNT + "/addressEdit.html");
        return LAYOUT;
    }

    @GetMapping("/Details")
    public String viewDetails(Model model) {
        model.addAttribute("content", PATH_ACOUNT + "/details.html");
        return LAYOUT;
    }

    @GetMapping("/Order")
    public String viewOrder(Model model) {
        model.addAttribute("content", PATH_ACOUNT + "/oder.html");
        return LAYOUT;
    }

    @GetMapping("/Orders")
    public String viewOrders(Model model) {
        model.addAttribute("content", PATH_ACOUNT + "/orders.html");
        return LAYOUT;
    }

}
