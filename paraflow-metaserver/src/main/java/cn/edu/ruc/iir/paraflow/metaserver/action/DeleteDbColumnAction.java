/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.ruc.iir.paraflow.metaserver.action;

import cn.edu.ruc.iir.paraflow.commons.exceptions.ActionParamNotValidException;
import cn.edu.ruc.iir.paraflow.commons.exceptions.DbColumnDeleteException;
import cn.edu.ruc.iir.paraflow.commons.exceptions.ParaFlowException;
import cn.edu.ruc.iir.paraflow.metaserver.connection.Connection;
import cn.edu.ruc.iir.paraflow.metaserver.connection.ResultList;
import cn.edu.ruc.iir.paraflow.metaserver.utils.SQLTemplate;

import java.util.Optional;

public class DeleteDbColumnAction extends Action
{
    @Override
    public ActionResponse act(ActionResponse input, Connection connection) throws ParaFlowException
    {
        Optional<Object> dbIdOp = input.getProperties("dbId");
        if (dbIdOp.isPresent()) {
            long dbId = (long) dbIdOp.get();
            String sqlStatement = SQLTemplate.findDbColumn(dbId);
            ResultList resultList = connection.executeQuery(sqlStatement);
            if (!resultList.isEmpty()) {
                //result
                String sqlStatement2 = SQLTemplate.deleteDbColumn(dbId);
                int status = connection.executeUpdate(sqlStatement2);
                if (status == 0) {
                    throw new DbColumnDeleteException();
                }
            }
        }
        else {
            throw new ActionParamNotValidException();
        }
        return input;
    }
}
