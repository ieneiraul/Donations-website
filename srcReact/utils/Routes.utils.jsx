import * as React from 'react';
import { useContext } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import Layout from '../components/Layout/Layout';
import ProfilePage from '../pages/ProfilePage';
import ProfileEditPage from '../pages/ProfileEditPage';
import LoginPage from '../pages/LoginPage';
import HomePage from '../pages/HomePage';
import RegisterPage from '../pages/RegisterPage';
import AuthContext from '../store/auth-context';
import StoriesPage from '../pages/StoriesPage';
import StoryPage from '../pages/StoryPage';
import StoryCreatePage from '../pages/StoryCreatePage';
import DonationCreatePage from '../pages/DonationCreatePage';

const Routes = () => {
    const authCtx = useContext(AuthContext);

    return(
    <Layout>
        <Switch>
          <Route path='/' exact>
            <HomePage />
          </Route>
          {/* {!authCtx.isLoggedIn && ( */}
            <Route path='/login'>
              <LoginPage />
            </Route>
          {/* )} */}
          {/* {!authCtx.isLoggedIn && ( */}
          <Route path='/registration'>
              <RegisterPage/>
            </Route>
          {/* )} */}
          <Route path='/users'>
            {/* {authCtx.isLoggedIn && <UserProfile />}
            {!authCtx.isLoggedIn && <Redirect to='/login' />} */}
            <h1>hi users</h1>
          </Route>
          <Route path='/donation/new'>
            {(authCtx.isLoggedIn && authCtx.role=="MANAGER") && <DonationCreatePage/>}
            {(!authCtx.isLoggedIn|| authCtx.role!=="MANAGER") && <Redirect to='/stories' />}
            
          </Route>
          <Route path='/stories/new'>
            {(authCtx.isLoggedIn && authCtx.role=="STUDENT") && <StoryCreatePage/>}
            {(!authCtx.isLoggedIn|| authCtx.role!=="STUDENT") && <Redirect to='/stories' />}
            
          </Route>
          <Route path='/stories/:id'>
            {authCtx.isLoggedIn && <StoryPage />}
            {!authCtx.isLoggedIn && <Redirect to='/login' />}
          </Route>
          <Route path='/stories'>
            {/* {authCtx.isLoggedIn && <UserProfile />}
            {!authCtx.isLoggedIn && <Redirect to='/login' />} */}
            <StoriesPage/>
          </Route>
          <Route  exact path='/profile/edit'>
              {authCtx.isLoggedIn && <ProfileEditPage />}
              {!authCtx.isLoggedIn && <Redirect to='/login' />}
            </Route>
          <Route path='/profile'>
            {authCtx.isLoggedIn && <ProfilePage />}
            {!authCtx.isLoggedIn && <Redirect to='/login' />}
          </Route>
          
          <Route path='*'>
            <Redirect to='/' />
          </Route>
        </Switch>
    </Layout>);
}

export default Routes;