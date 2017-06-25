package by.darashchonak.mentoring.nosql.contoller;

import by.darashchonak.mentoring.nosql.api.TaskDao;
import by.darashchonak.mentoring.nosql.model.TaskBackObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mikhail_Darashchonak on 6/23/17.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private TaskDao taskDao;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String mainPage(Model model) {

        model.addAttribute("tasks", taskDao.getAll());

        return "index";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String showTaskForm(Model model){
        model.addAttribute("title", "New Task");
        model.addAttribute("button", "Add");

        return "add";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute TaskBackObject task, @RequestParam String id){

        if (id.trim().isEmpty()) {
            taskDao.save(task);
        } else {
            taskDao.update(id, task);
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(@RequestParam String id, @RequestParam String action, Model model) {

        if (action.trim().equals("update")) {
            model.addAttribute("task", taskDao.findById(id));
            model.addAttribute("title", "Update Task");
            model.addAttribute("button", "Update");
        }

        if (action.trim().equals("remove")) {
            taskDao.delete(id);

            return "redirect:/";
        }
        return "add";
    }

    public String remove() {
        return "index";
    }

    @RequestMapping(path = "/category/{category}", method = RequestMethod.GET)
    public String showCategory(@PathVariable String category, Model model) {

        model.addAttribute("tasks", taskDao.findByCategory(category));

        return "index";
    }

    @RequestMapping(path = "/overdue", method = RequestMethod.GET)
    public String showOverdue(Model model) {

        model.addAttribute("tasks", taskDao.getOverdue());

        return "index";

    }

}
