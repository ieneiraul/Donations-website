import { useContext } from 'react';
import { Link } from 'react-router-dom';

import AuthContext from '../store/auth-context';
import classes from './MainNavigation.module.css';
import HomeIcon from '@mui/icons-material/Home';


const MainNavigation = () => {
  const authCtx = useContext(AuthContext);

  const isLoggedIn = authCtx.isLoggedIn;

  const logoutHandler = () => {
    authCtx.logout();
    // optional: redirect the user
  };

  return (
    <header className={classes.header}>
      <Link to='/'>
        <div className={classes.logo}><HomeIcon fontSize="large"/></div>
      </Link>
      <nav>
        <ul>
          {!isLoggedIn && (
            <li>
              <Link to='/login'>Login</Link>
            </li>
          )}
          {!isLoggedIn && (
            <li>
              <Link to='/registration'>Inregistrare</Link>
            </li>
          )}
          {isLoggedIn && (
            <li>
              <Link to='/profile'>Profil</Link>
            </li>
          )}
          {isLoggedIn && (
            <li>
              <button onClick={logoutHandler}>Logout</button>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default MainNavigation;