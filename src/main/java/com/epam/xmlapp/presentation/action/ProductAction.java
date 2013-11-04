package com.epam.xmlapp.presentation.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.MappingDispatchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.presentation.facade.ProductFacade;
import com.epam.xmlapp.presentation.form.ProductForm;

public class ProductAction extends MappingDispatchAction
{	
	private static final Logger log = LoggerFactory.getLogger(ProductAction.class);

	private static final String MAIN_PAGE = "mainpage";
	private static final String PRODUCT_LIST_PAGE = "productList";
	private static final String PARSER_PAGE = "parser";
	private static final String PREVIOUS_PAGE = "previousPage";
	private static final String ERROR_PAGE = "error";

	private ProductFacade productFacade;
	
	public ProductAction()
	{
		log.info("com.epam.xmlapp.presentation.action.ProductAction has been created");
	}

	public void setProductFacade(ProductFacade productFacade)
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public void setProductFacade(ProductFacade productFacade): productFacade={}, ProductAction={}", productFacade, this);
		this.productFacade = productFacade;
	}

	public ActionForward productsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward productsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/productsList");	
		String target = ERROR_PAGE;
		ProductForm productForm = (ProductForm) actionForm;
		if (null != productForm)
		{
			if (null != productFacade)
			{
				List<Category> categories = productFacade.parse();
				log.debug("Date got from productFacade.parse() in action=/productsList: categories={}", categories);
				productForm.setCategoryList(categories);
				target = PRODUCT_LIST_PAGE;
			}
			else
			{
				log.warn("Error in springframework. Bean productFacade was not linked to com.epam.xmlapp.presentation.action.ProductAction: productFacade={}", productFacade);
				productForm.setCategoryList(new ArrayList<Category>());
			}
//			
//			Product prod = new Product();
//			prod.setColor("black");
//			prod.setModel("chainik");
//			prod.setPrice("no");
//			prod.setProductName("Bal'skoi chainik");
//			prod.setProvider("Fabrika");
//			prod.setDateOfIssue(new java.util.Date(System.currentTimeMillis()));
//			List<Product> prods = new ArrayList<Product>();
//			prods.add(prod);
//			productForm.setProductList(prods);
		}
		else
		{
			log.warn("productForm in /productsList is null.");
		}
		log.info("/productsList action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /productsList action: target={}", target);
		return actionMapping.findForward(target);
	}

	public ActionForward parser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward parser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/parser");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, PARSER_PAGE);
		ProductForm productForm = (ProductForm) actionForm;
		if (null != productForm)
		{
			target = PARSER_PAGE;
		}
		else
		{
			log.warn("productForm in /parser is null.");
		}
		log.info("/parser action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /parser action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward changeParser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward changeParser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/changeParser");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, MAIN_PAGE);
		ProductForm productForm = (ProductForm) actionForm;
		if (null != productForm)
		{
			target = PARSER_PAGE;
			String parserName = productForm.getParserName();
			log.debug("Args in /changeParser action: parserName={}", parserName);
			if (parserName != null) 
			{
				productFacade.setParser(parserName);
				if (null != productFacade.getParser())
				{
					target = MAIN_PAGE;
				}
			}		
			else
			{
				log.debug("parserName in /changeParser is null");
			}
		}
		else
		{
			log.warn("productForm in /changeParser is null.");
		}
		log.info("/changeParser action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /changeParser action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("ccom.epam.xmlapp.presentation.action.ProductAction public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)");
		log.info("ProductActionServlet: action=/cancel");
		String target = (String) request.getSession().getAttribute(PREVIOUS_PAGE);
		log.debug("actionMapping.findForward(target) in /back action: target={}", target);
		return actionMapping.findForward(target);
	}

	public ActionForward error(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward error(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)");
		log.info("ProductActionServlet: action=/error");
		String target = ERROR_PAGE;
		log.debug("actionMapping.findForward(ERROR_PAGE) in /error action: target={}", ERROR_PAGE);
		return actionMapping.findForward(target);
	}
}
