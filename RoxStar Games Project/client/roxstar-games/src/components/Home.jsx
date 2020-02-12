import React from 'react';
import { withStyles } from "@material-ui/core/styles";
import { AppBar, Toolbar, Typography, Button, Grid } from '@material-ui/core';
import GameCard from "./GameCard.jsx";
import BANNER from "../assets/images/banner.gif";
import DELIVERY from "../assets/images/free_delivery.png";
import sendRequest from '../Request.js';
import { NavLink } from 'react-router-dom';

const styles = theme => ({
  rowGames: {
    marginBottom: 50
  }
});

var CustomAppBar = (props) => {
  return (
    <AppBar position="static" style={{ backgroundColor: "darkred", margin: "30px 0px" }}>
      <Toolbar>
        <Grid container>
          <Grid item xs={11}>
            <Typography variant="h6">
              {props.text}
            </Typography>
          </Grid>
          <Grid item xs={1} >
            <Button color="inherit">
              <NavLink to={props.link}>view all &gt;</NavLink>
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
}

class Home extends React.Component {
  constructor() {
    super();
    this.state = { products: [] , productsOnSale: [] };
  }

  componentDidMount() {
    const uri = '/api/products';
    sendRequest(uri + '/new', 'GET', {}, (response) => {
      this.setState({ products: response });
    });
    sendRequest(uri + '/onSale', 'GET', {}, (response) => {
      this.setState({ productsOnSale: response });
    });
  }

  render() {
    const { classes } = this.props;
    return (
      <div>
        <Grid container spaing={3} justify="center">
          <Grid item xs={5} className={classes.banner}>
            <img src={BANNER} alt="banner" />
          </Grid>
          <Grid item xs={3}>
            <img src={DELIVERY} alt="delivery" className={classes.banner} />
            <Typography variant="h5" style={{ color: "darkred", fontWeight: "bold" }}>
              Telephone: +359 89 432 354
            </Typography>
          </Grid>
          <Grid item xs={8} className={classes.rowGames}>
            <CustomAppBar text="New And Upcoming Releases" link="/catalogue/new" />
            <Grid container spacing={2}>
              {this.state.products.slice(0,4).map(game =>
                <Grid key={game.id} item xs={3}>
                  <GameCard game={game}/>
                </Grid>
              )}
            </Grid>
          </Grid>
          <Grid item xs={8} className={classes.rowGames}>
            <CustomAppBar text="On Sale" link="/catalogue/onSale" />
            <Grid container spacing={2}>
              {this.state.productsOnSale.slice(0,4).map(game =>
                <Grid key={game.id} item xs={3}>
                  <GameCard game={game}/>
                </Grid>
              )}
            </Grid>
          </Grid>
        </Grid>
      </div>
    );
  }
}


export default withStyles(styles, { withTheme: true })(Home);