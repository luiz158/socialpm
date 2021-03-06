/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM)
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM
 * under the terms of the GPL, as follows:
 *
 * SocialPM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SocialPM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SocialPM.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 * 
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their
 * products, or host their product online, OCPsoft provides flexible
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.faces.listener;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.faces.listener.events.TaskCommand;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.project.Stories;
import com.ocpsoft.socialpm.services.project.StoryService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class TaskCommandListener
{
   private StoryService ss;
   private final List<TaskCommand> commands = new ArrayList<TaskCommand>();
   private Story story;

   protected TaskCommandListener()
   {}

   @Inject
   public TaskCommandListener(@Web EntityManager em, StoryService ss,
            Stories stories)
   {
      ss.setEntityManager(em);
      this.ss = ss;
      this.story = stories.getCurrent();
   }

   public void capture(@Observes TaskCommand command)
   {
      this.commands.add(command);
   }

   @TransactionAttribute
   public void save(Task t)
   {
      for (TaskCommand c : commands) {
         c.perform(t);
      }
      ss.save(story);
   }

}
