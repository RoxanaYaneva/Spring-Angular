import React from 'react';
import { NavLink } from 'react-router-dom';
import { Grid, Toolbar, AppBar, Button, Paper, Tabs, Tab } from '@material-ui/core';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import { withStyles } from "@material-ui/core/styles";
import LOGO1 from '../assets/images/bottom1.png';
import Searchbar from './SearchBar.jsx';

const styles = theme => ({
  root: {
    height: 250,
    backgroundColor: "red"
  },
  appbar: {
    backgroundColor: "salmon",
    color: "darkred"
  },
  logo: {
    textAlign: "right"
  },
  searchbar: {
    textAlign: "center",
    marginTop: 30,
    marginLeft: 130
  },
  navmenu: {
    backgroundColor: "darkred",
    color: "white"
  },
  tab: {
    width: 350,
    fontSize: 22,
    fontWeight: "bold"
  }
});

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.state = { input: '' };
  }
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <AppBar position="static" className={classes.appbar}>
              <Grid container spacing={2} justify="flex-end">
                <Grid item xs={6} sm={4}>
                  <Toolbar className={classes.toolbar}>
                    {/* <Button>
                      <NavLink to="/adminPanel">My pannel</NavLink>
                    </Button> */}
                    <Button>
                      <NavLink to="/account">My Account</NavLink>
                    </Button>
                    <Button>
                      <NavLink to="/login">Login</NavLink>
                    </Button>
                    <Button>
                      <NavLink to="/cart">Cart</NavLink>
                      <ShoppingCartIcon />
                    </Button>
                  </Toolbar>
                </Grid>
              </Grid>
            </AppBar>
          </Grid>
          <Grid item xs={4}>
            <NavLink to="/">
              <img src={LOGO1} alt="logo" style={{ width: 250, float: "right" , marginTop: -30}} />
            </NavLink>
          </Grid>
          <Grid item xs={5} className={classes.searchbar}>
            <Searchbar history={this.props.history} />
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <AppBar position="static">
            <Paper className={classes.navmenu}>
              <Tabs centered>
                <Tab className={classes.tab} label="Shop By Studio" to='/catalogue' component={NavLink} />
                <Tab className={classes.tab} label="Shop By Platform" to='/catalogue' component={NavLink} />
                <Tab className={classes.tab} label="Shop By Genre" to='/catalogue' component={NavLink} />
                <Tab className={classes.tab} label="Shop By Type" to='/catalogue' component={NavLink} />
                <Tab className={classes.tab} label="On Sale" to='/catalogue/onSale' component={NavLink} />
              </Tabs>
            </Paper>
          </AppBar>
        </Grid>
      </div>
    );
  }
}

export default withStyles(styles, { withTheme: true })(Header);