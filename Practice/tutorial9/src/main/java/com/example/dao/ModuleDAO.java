package com.example.dao;

import com.example.model.Module;
import com.example.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class ModuleDAO {
    private static final List<Module> modules = new ArrayList<Module>();

    static {
        modules.add(new Module(1, "Chemistry", new Teacher(1, "Mr. Smith")));
        modules.add(new Module(2, "Physics", new Teacher(2, "Mrs. Jones")));
    }

    public List<Module> getAllModules() {
        return modules;
    }

    public Module getModuleById(int id) {
        for (Module module : modules) {
            if (module.getId() == id) {
                return module;
            }
        }
        return null;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void updateModule(Module module) {
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId() == module.getId()) {
                modules.set(i, module);
            }
        }
    }

    public void deleteModule(int id) {
        modules.removeIf(module -> module.getId() == id);
    }

    public int getNextModuleId() {
        int maxModuleId = Integer.MIN_VALUE;
        for (Module module : modules) {
            if (maxModuleId < module.getId()) {
                maxModuleId = module.getId();
            }
        }
        return maxModuleId + 1;
    }
}
