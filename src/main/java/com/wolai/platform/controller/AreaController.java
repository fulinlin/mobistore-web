package com.wolai.platform.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.Area;
import com.wolai.platform.service.AreaService;

/**
 * 区域Controller
 * @author sevenshi
 */
@Controller
@RequestMapping(value = "${adminPath}/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    @ModelAttribute("area")
    public Area get(@RequestParam(required = false) String id) {
        Area entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = (Area) areaService.get(Area.class, id);
        }
        if (entity == null) {
            entity = new Area();
        }
        return entity;

    }

    @RequestMapping(value = { "list", "" })
    public String list(Area area, Model model) {
        DetachedCriteria dc = DetachedCriteria.forClass(Area.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        model.addAttribute("list", areaService.findAllByCriteria(dc));
        return "sys/area/areaList";
    }

    @RequestMapping(value = "form")
    public String form(Area area, Model model) {
        if(StringUtils.isNotBlank(area.getParentId())){
            area.setParent((Area) areaService.get (Area.class,area.getParentId()));
        }
        model.addAttribute("area", area);
        return "sys/area/areaForm";
    }

    @RequestMapping(value = "save")
    public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, area)) {
            return form(area, model);
        }
        area.setParent(null);
        areaService.saveOrUpdate(area);
        addMessage(redirectAttributes, "保存区域'" + area.getName() + "'成功");
        return "redirect:" + SystemConfig.getAdminPath() + "/area/";
    }

    @RequestMapping(value = "delete")
    public String delete(Area area, RedirectAttributes redirectAttributes) {
        areaService.delete(area);
        addMessage(redirectAttributes, "删除区域成功");
        return "redirect:" + SystemConfig.getAdminPath() + "/area/";
    }

    @ResponseBody
    @RequestMapping(value = "treeData")
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        DetachedCriteria dc = DetachedCriteria.forClass(Area.class);
        List<Area> list = areaService.findAllByCriteria(dc);
        for (int i = 0; i < list.size(); i++) {
            Area e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }
}
