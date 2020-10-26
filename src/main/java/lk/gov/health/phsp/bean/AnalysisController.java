/*
 * The MIT License
 *
 * Copyright 2020 chims.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lk.gov.health.phsp.bean;

// <editor-fold defaultstate="collapsed" desc="Imports">
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import lk.gov.health.phsp.enums.EncounterType;
import lk.gov.health.phsp.facade.ClientFacade;
import lk.gov.health.phsp.facade.EncounterFacade;
// </editor-fold>

@Named
@ApplicationScoped
public class AnalysisController {
// <editor-fold defaultstate="collapsed" desc="EJBs">

    @EJB
    private ClientFacade clientFacade;
    @EJB
    private EncounterFacade encounterFacade;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Class Variables">
    private Long clientCount;
    private Long encounterCount;
    private Date from;
    private Date to;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Constructors">
    public AnalysisController() {
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Navigation Methods">
    public String toCountsForSystemAdmin() {
        return "/systemAdmin/all_counts";
    }
// </editor-fold>    

// <editor-fold defaultstate="collapsed" desc="Main Methods">
    public void findEncounterCount() {
        Long fs;
        Map m = new HashMap();
        String j = "select count(s) from Encounter s ";
        j += " where s.retired<>:ret ";
        j += " and s.encounterType=:t ";
        j += " and s.encounterDate between :fd and :td ";
        m.put("fd", getFrom());
        m.put("td", getTo());
        m.put("t", EncounterType.Clinic_Visit);
        m.put("ret", true);

        fs = getEncounterFacade().findLongByJpql(j, m);

        encounterCount = fs;
    }

    public void findClientCount() {
        String j = "select count(c) from Client c "
                + " where c.retired<>:ret ";
        Map m = new HashMap();
        m.put("ret", true);

        j = j + " and c.createdAt between :fd and :td ";
        m.put("fd", getFrom());
        m.put("td", getTo());

        clientCount = getClientFacade().findLongByJpql(j, m);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ClientFacade getClientFacade() {
        return clientFacade;
    }

    public void setClientFacade(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    public EncounterFacade getEncounterFacade() {
        return encounterFacade;
    }

    public void setEncounterFacade(EncounterFacade encounterFacade) {
        this.encounterFacade = encounterFacade;
    }

    public Long getClientCount() {
        return clientCount;
    }

    public void setClientCount(Long clientCount) {
        this.clientCount = clientCount;
    }

    public Long getEncounterCount() {
        return encounterCount;
    }

    public void setEncounterCount(Long encounterCount) {
        this.encounterCount = encounterCount;
    }

    public Date getFrom() {
        if (from == null) {
            from = CommonController.startOfTheMonth();
        }
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        if (to == null) {
            to = new Date();
        }
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
// </editor-fold>

}
