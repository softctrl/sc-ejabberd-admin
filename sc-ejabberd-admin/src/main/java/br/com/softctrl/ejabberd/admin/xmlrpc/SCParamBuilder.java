package br.com.softctrl.ejabberd.admin.xmlrpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import br.com.softctrl.utils.Objects;

/*
The MIT License (MIT)

Copyright (c) 2016 Carlos Timoshenko Rodrigues Lopes
http://www.0x09.com.br

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
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
