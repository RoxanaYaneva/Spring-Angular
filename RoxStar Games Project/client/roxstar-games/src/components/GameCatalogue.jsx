import React from 'react';
import sendRequest from '../Request.js';
import Grid from "@material-ui/core/Grid";
import GameCard from './GameCard.jsx';
import { MenuList, MenuItem, withStyles } from '@material-ui/core'; 

const styles = theme => ({
  menu : {
    color: "darkred",
    fontWeight: "bold",
    fontSize: 25,
    borderRight: "3px solid darkred"
  },
  list : {
    marginBottom: 20
  }
});

class GameCatalogue extends React.Component {
  constructor() {
    super();
    this.state = { products: [] , pages : 0 };
  }

  componentDidMount() {
    let sale = document.URL.substring(document.URL.lastIndexOf('/') + 1);
    let uri = '';
    if (sale === 'onSale') {
      uri = '/api/products/onSale';
    } else {
      uri = '/api/products';
    }
    sendRequest(uri, 'GET', {}, (response) => {
      this.setState({ products: response });
    });
  }

  handleClick(event, category, value) {
    event.preventDefault();
    const uri = `/api/products?${category}=${value}`;
    sendRequest(uri, 'GET', {}, (response) => {
      this.setState({ products: response });
    });
  }

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <Grid container justify="center">
          <Grid item xs={2} className={classes.menu}>
            <MenuList> Platform
              <MenuItem onClick={event => this.handleClick(event, "platform", "Xbox 360")}>Xbox 360</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "platform", "Xbox One")}>Xbox One</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "platform", "PS 2")}>PS 2</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "platform", "PS 4")}>PS 4</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "platform", "PC")}>PC</MenuItem>
            </MenuList>
            <MenuList> Studio
              <MenuItem onClick={event => this.handleClick(event, "studio", "Rockstar Games")}>Rockstar Games</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "studio", "Ubisoft")}>Ubisoft</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "studio", "Sony")}>Sony</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "studio", "Nintendo")}>Nintendo</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "studio", "Blizzard")}>Blizzard</MenuItem>
            </MenuList>
            <MenuList> Genre
              <MenuItem onClick={event => this.handleClick(event, "genre", "action")}>Action</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "genre", "strategy")}>Strategy</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "genre", "escape")}>Escape</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "genre", "adventure")}>Adventure</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "genre", "sport")}>Sports</MenuItem>
            </MenuList>
            <MenuList> Type
              <MenuItem onClick={event => this.handleClick(event, "type", "Single-player")}>Single-player</MenuItem>
              <MenuItem onClick={event => this.handleClick(event, "type", "Multiplayer")}>Multiplayer</MenuItem>
            </MenuList>
          </Grid>
          <Grid item xs={1}></Grid>
          <Grid item xs={6}>
            <Grid style={{marginBottom: 15}} container justify="flex-start" spacing={3}>
                {this.state.products.map(game =>
                  <Grid item xs={4}>
                     <GameCard key={game.id} game={game} />
                  </Grid>
                )}
            </Grid>   
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default withStyles(styles, { withTheme: true })(GameCatalogue);