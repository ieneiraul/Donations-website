import { Fragment } from 'react';

import MainNavigation from '../MainNavigation';

const Layout = (props) => {
  return (
    <Fragment className="body-height">
      <MainNavigation />
      <main >{props.children}</main>
      
    </Fragment>
  );
};

export default Layout;
