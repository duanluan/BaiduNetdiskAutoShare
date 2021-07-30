package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * ${table.comment!} 服务
 *
 * @author ${author}
<#if date != "">
 * @since ${date}
</#if>
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>