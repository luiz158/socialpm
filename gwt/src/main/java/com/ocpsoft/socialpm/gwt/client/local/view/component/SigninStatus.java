package com.ocpsoft.socialpm.gwt.client.local.view.component;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class SigninStatus extends Composite
{
   @Inject
   private UiBinder<Widget, SigninStatus> binder;

   @UiField
   Span signedIn;

   @UiField
   Span signedOut;

   private final ProfileLink profileLink = new ProfileLink();
   private final NavLink signinLink = new NavLink("Sign in", HistoryConstants.LOGIN());

   @PostConstruct
   public void postConstruct()
   {
      binder.createAndBindUi(this);

      signedIn.add(new Span("Signed in as "));
      signedIn.add(profileLink);
      signedIn.setVisible(false);

      signedOut.add(signinLink);
      signedOut.setVisible(true);
   }

   public void handleLogin(@Observes LoginEvent event)
   {
      this.setSignedIn(event.getProfile());
   }

   public SigninStatus setSignedIn(Profile profile)
   {
      profileLink.setProfile(profile);
      signedIn.setVisible(true);
      signedOut.setVisible(false);
      return this;
   }

   public SigninStatus setSignedOut()
   {
      signedIn.setVisible(false);
      signedOut.setVisible(true);
      return this;
   }

   public void addSigninClickHandler(ClickHandler clickHandler)
   {
      signinLink.addClickHandler(clickHandler);
   }

   public Anchor getProfileLink()
   {
      return profileLink;
   }

   public Anchor getSigninLink()
   {
      return signinLink;
   }

}
