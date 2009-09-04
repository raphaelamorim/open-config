package org.openconfig.providers.ast.transformers;

import org.openconfig.providers.ast.ComplexNode;

import java.util.Map;

/**
 * Converts a map to a complex node.
 *
 * @author Dushyanth (Dee) Inguva
 */
public class MapToNodeTransformer implements Transformer<Map<String, String>, ComplexNode>{

    public ComplexNode transform(Map<String, String> source) throws Exception {

        ComplexNode root = new ComplexNode("root");
        for(Map.Entry<String, String> mapping: source.entrySet()) {
        }
        return null;
    }
}
