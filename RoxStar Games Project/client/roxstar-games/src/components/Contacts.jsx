import React from 'react';
import FacebookIcon from '@material-ui/icons/Facebook';
import InstagramIcon from '@material-ui/icons/Instagram';
import TwitterIcon from '@material-ui/icons/Twitter';
import MailOutlineIcon from '@material-ui/icons/MailOutline';
import PhoneIcon from '@material-ui/icons/Phone';

function Contacts() {
  return (
    <div style={{ margin: 100, fontSize: 20, textAlign: "justify" }}>
      <h1>Contacts</h1>
      <h2><FacebookIcon fontSize="large" /> Facebook:</h2>
      <p><a href="https://www.facebook.com/RoxStarGames">https://www.facebook.com/RoxStarGames</a></p>
      <h2><TwitterIcon fontSize="large" /> Twitter:</h2>
      <p><a href="https://www.twitter.com/RoxStarGames">https://www.twitter.com/RoxStarGames</a></p>
      <h2><InstagramIcon fontSize="large" /> Instagram:</h2>
      <p><a href="https://www.instagram.com/RoxStarGames">https://www.instagram.com/RoxStarGames</a></p>
      <h2><MailOutlineIcon fontSize="large" /> Mail:</h2>
      <p><a href="https://www.gmail.com/RoxStarGames">https://www.gmail.com/RoxStarGames</a></p>
      <h2><PhoneIcon fontSize="large" /> Telephone:</h2>
      <p>+453 343 2277</p>
    </div>
  )
}

export default Contacts;