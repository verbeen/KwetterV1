package kwetter.service;

import kwetter.dao.interfaces.UserDAO;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;

/**
 * Created by geh on 4-4-14.
 */

@Singleton @Startup
public class KwetterScheduler
{
    @Inject
    private UserDAO userDAO;
    @Resource
    private TimerService timerService;

    @PostConstruct
    public void startTimers()
    {
        ScheduleExpression se = new ScheduleExpression().hour("*").minute("*").second("*/5");
        timerService.createCalendarTimer(se, new TimerConfig("application timeout check", false));
    }

    @Timeout
    public void timeOut(Timer timer)
    {
        this.userDAO.removeOldApplications();
    }

    //@Schedule(hour="*", minute = "*", second = "*/5", persistent = false)
    //public void timeOut()
    //{
    //    this.userDAO.removeOldApplications();
    //}
}
