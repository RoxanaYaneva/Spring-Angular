import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import Notifications from 'react-notify-toast';
import Home from './components/Home';
import Login from './components/Login';
import Header from './components/Header';
import Cart from './components/Cart';
import Help from './components/Help.jsx';
import GameCatalogue from './components/GameCatalogue';
import Register from './components/Register';
import Footer from './components/Footer';
import Contacts from './components/Contacts';
import GameDetail from './components/GameDetail';
import UserProfile from './components/UserProfile';
import AddProduct from './components/AddProduct';
import AdminPanel from './components/AdminPanel';


class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <div style={{ backgroundColor: "lightyellow" }}>
          <Notifications options={{ zIndex: 200, top: 20 }} />
          <Header />
          <div style={{marginTop: 100, minHeight: 710}}>
            <Route path="/" component={Home} exact />
            <Route path="/login" component={Login} />
            <Route path="/register" component={Register} />
            <Route path="/cart" component={Cart} />
            <Route path="/help" component={Help} />
            <Route path="/contacts" component={Contacts} />
            <Route path="/game" component={GameDetail} />
            <Route path="/catalogue" component={GameCatalogue} />
            <Route path="/account" component={UserProfile} />
            <Route path="/addProduct" component={AddProduct} />
            <Route path="/adminPanel" component={AdminPanel} />
          </div>
        </div>
        <Footer />
      </BrowserRouter>
    );
  }
}

export default App;