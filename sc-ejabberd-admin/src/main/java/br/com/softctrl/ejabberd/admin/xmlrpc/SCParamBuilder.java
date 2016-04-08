package br.com.softctrl.ejabberd.admin.xmlrpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import br.com.softctrl.utils.Objects;

public class SCParamBuilder {

    public interface ISCAuthProcess {
        List<Object> auth(Hashtable<String, Object> params);
    }

    private final Hashtable<String, Object> mParams = new Hashtable<String, Object>();
    private final ISCAuthProcess mProcess;

    public SCParamBuilder() { this(null, null); }

    public SCParamBuilder(ISCAuthProcess process) { this(process, null); }

    public SCParamBuilder(ISCAuthProcess process, Map<String, Object> params) {
        if (Objects.nonNullOrEmpty(params))
            this.mParams.putAll(params);
        if (Objects.nonNull(process)) {
            mProcess = process;
        } else {
            mProcess = new ISCAuthProcess() {
                @Override
                public List<Object> auth(Hashtable<String, Object> params) {
                    List<Object> result = new ArrayList<Object>();
                    result.add(params);
                    return result;
                }
            };
        }
    }

    public SCParamBuilder addParam(String name, Object value) {
        this.mParams.put(name, value);
        return this;
    }

    public List<Object> getParams() {
        return Objects.requireNonNull(this.mProcess).auth(this.mParams);
    }

    public SCParamBuilder clear() {
        this.mParams.clear();
        return this;
    }

    public static final Object[] param(String name, Object value) {
        return new Object[] { name, value };
    }
}
