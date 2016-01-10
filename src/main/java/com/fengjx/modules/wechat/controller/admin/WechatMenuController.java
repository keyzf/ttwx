
package com.fengjx.modules.wechat.controller.admin;

import com.fengjx.commons.utils.JsonUtil;
import com.fengjx.modules.wechat.model.WechatMenu;
import com.fengjx.modules.common.controller.MyController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author fengjx.
 * @date：2015/6/9 0009
 */
@Controller
@RequestMapping("${adminPath}/wechat/menu")
public class WechatMenuController extends MyController {

    @Autowired
    private WechatMenu wechatMenu;

    /**
     * 菜单管理界面
     * 
     * @return
     */
    @RequestMapping(value = "")
    public String view() {
        return "/wechat/admin/menu";
    }

    /**
     * 加载树形菜单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/load")
    @ResponseBody
    public List<Map<String, Object>> load(HttpServletRequest request) {
        List<Map<String, Object>> tree = wechatMenu.treeMenu(getLoginSysUserId());
        return tree;
    }

    /**
     * 更新 / 保存菜单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdate(HttpServletRequest request) {
        Map<String, Object> attrs = getNotBlankRequestMap(request);
        attrs.put("user_id", getLoginSysUserId());
        wechatMenu.saveOrUpdate(attrs);
        return retSuccess();
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(String id) {
        wechatMenu.deleteMenu(id);
        return retSuccess();
    }

    /**
     * 发布菜单
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/release")
    @ResponseBody
    public String release(HttpServletRequest request) {
        wechatMenu.release(getLoginSysUserId());
        return retSuccess();
    }

    @RequestMapping(value = "/sort")
    @ResponseBody
    public String sort(HttpServletRequest request, String sortStr) {
        List<Map<String, Object>> sortJson = StringUtils.isNotBlank(sortStr) ? JsonUtil
                .parseJSON2List(sortStr) : null;
        wechatMenu.sort(sortJson, getLoginSysUserId());
        return retSuccess();
    }

}