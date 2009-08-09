package org.openconfig.core;

import org.openconfig.core.bean.PropertyNormalizer;

/**
 * @author Richard L. Burton III
 */
public interface PropertyNormalizerable {

    void setPropertyNormalizer(PropertyNormalizer propertyNormalizer);

}
