package org.openiam.idm.srvc.meta.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data service implementation for Metadata.
 * @author suneet
 * @version 1
 */


public class MetadataServiceImpl implements MetadataService {

	private MetadataTypeDAO metadataTypeDao;
	private MetadataElementDAO metadataElementDao;

    private static final Log log = LogFactory.getLog(MetadataServiceImpl.class);

	@Transactional
	public MetadataElement addMetadataElement(MetadataElement metadataElement) {
		if (metadataElement == null) {
			throw new NullPointerException("metadataElement is null");
		}
		metadataElementDao.add(metadataElement);
		return metadataElement;
	}

    @Transactional
	public MetadataType addMetadataType(MetadataType type) {
		if (type == null) {
			throw new NullPointerException("Metadatatype is null");
		}
		metadataTypeDao.add(type);
		return type;

	}

    @Transactional
	public void addTypeToCategory(String typeId, String categoryId) {
		if (typeId == null)
			throw new NullPointerException("typeId is null");
		if (categoryId == null)
			throw new NullPointerException("category is null");

		this.metadataTypeDao.addCategoryToType(typeId, categoryId);
    }

    @Transactional(readOnly = true)
	public MetadataElement getMetadataElementById(String elementId) {
		if (elementId == null) {
			throw new NullPointerException("elementId is null");
		}
		return metadataElementDao.findById(elementId);
	}

    @Transactional(readOnly = true)
	public MetadataElement[] getMetadataElementByType(String typeId) {
		if (typeId == null) {
			throw new NullPointerException("typeId is null");
		}
		MetadataType type = metadataTypeDao.findById(typeId);
		if (type == null ) 
			return null;
		Map<String, MetadataElement> elementMap = type.getElementAttributes();		
		if (elementMap == null || elementMap.isEmpty()) {
			return null;
		}
		// convert to an array
		Collection<MetadataElement> elementCollection = elementMap.values();
		MetadataElement[] elementAry = new MetadataElement[elementCollection.size()];
		elementCollection.toArray(elementAry);
		return elementAry;
	}

    @Transactional(readOnly = true)
	public MetadataType getMetadataType(String typeId) {
		if (typeId == null) {
			throw new NullPointerException("typeId is null");
		}
		return metadataTypeDao.findById(typeId);
	}

    @Transactional(readOnly = true)
	public MetadataType[] getMetadataTypes() {
		List<MetadataType> typeList =  metadataTypeDao.findAll();
		if (typeList == null || typeList.isEmpty()) {
			return null;
		}
		
		int size = typeList.size();
		MetadataType[] typeAry = new MetadataType[size];
		typeList.toArray(typeAry);
		
		return typeAry;
	}

    @Transactional(readOnly = true)
	public MetadataType[] getTypesInCategory(String categoryId) {
	
		if (categoryId == null) {
			throw new NullPointerException("categoryId is null");
		}
		
		List<MetadataType> typeList = metadataTypeDao.findTypesInCategory(categoryId);
		if (typeList == null || typeList.isEmpty()) {
			return null;
		}
		int size = typeList.size();
		MetadataType[] typeAry = new MetadataType[size];
		typeList.toArray(typeAry);
		return typeAry;
	}

    @Transactional
	public void removeMetadataElement(String elementId) {
		if (elementId == null) {
			throw new NullPointerException("elementId is null");
		}
		MetadataElement element = new MetadataElement(elementId);
		metadataElementDao.remove(element);
	}

    @Transactional
	public void removeMetadataType(String typeId) {
		if (typeId == null) {
			throw new NullPointerException("typeId is null");
		}
		
		metadataElementDao.removeByParentId(typeId);
		
		MetadataType type = new MetadataType();
		type.setMetadataTypeId(typeId);
		metadataTypeDao.remove(type);
	}

    @Transactional
	public void removeTypeFromCategory(String typeId, String categoryId) {
		if (typeId == null)
			throw new NullPointerException("typeId is null");
		if (categoryId == null)
			throw new NullPointerException("category is null");

		metadataTypeDao.removeCategoryFromType(typeId, categoryId);
	}

    @Transactional
	public MetadataElement updateMetadataElement(MetadataElement mv) {
		if (mv == null) {
			throw new NullPointerException("metadataElement is null");
		}
		metadataElementDao.update(mv);
		return mv;
	}

    @Transactional
	public MetadataType updateMetdataType(MetadataType type) {
		if (type == null) {
			throw new NullPointerException("Metadatatype is null");
		}

        log.debug("updateMetdataType: " + type);

		metadataTypeDao.update(type);
		return type;

	}

    @Transactional(readOnly = true)
	public List<MetadataElement> getAllElementsForCategoryType(String categoryType) {
		if (categoryType == null) {
			throw new NullPointerException("categoryType is null");
		}
		return metadataElementDao.findbyCategoryType(categoryType);		 
	 }

	public MetadataTypeDAO getMetadataTypeDao() {
		return metadataTypeDao;
	}

	public void setMetadataTypeDao(MetadataTypeDAO metadataTypeDao) {
		this.metadataTypeDao = metadataTypeDao;
	}

	public MetadataElementDAO getMetadataElementDao() {
		return metadataElementDao;
	}

	public void setMetadataElementDao(MetadataElementDAO metadataElementDao) {
		this.metadataElementDao = metadataElementDao;
	}

}
