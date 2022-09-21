
import { BrowserRouter } from 'react-router-dom';
import { AuthContextProvider } from './store/auth-context';
import Routes from './utils/Routes.utils';




function App() {

  return (
    <AuthContextProvider>
      <BrowserRouter>
        <Routes/>
      </BrowserRouter>
    </AuthContextProvider>
    
      
  );
    // <div className="App">
    //   <NavBarComponent></NavBarComponent>
    //   {/* <UserComponent></UserComponent> */}
    // </div>
    
}

export default App;
