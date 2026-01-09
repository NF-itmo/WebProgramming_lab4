import "./Main.css";

import Form from "../../modules/plotForm/PlotForm";
import { Loader } from "@packages/shared";
import Header from "../../components/header/Header";
import React from "react";
import MainPageSkeleton from "../../UI/mainPageSkeleton/MainPageSkeleton";

const PlotComponent = React.lazy(() =>
  import('plotComponent/Main').catch(() => ({
    default: () => <div>Error loading Plot component</div>,
  }))
);
const HistoryComponent = React.lazy(() =>
  import('historyComponent/Main').catch(() => ({
    default: () => <div>Error loading History component</div>,
  }))
);

const GroupsComponent = React.lazy(() =>
  import('groupComponent/Main').catch(() => ({
    default: () => <div>Error loading Group component</div>,
  }))
);

const TopbarComponent = React.lazy(() =>
  import('topbarComponent/Main').catch(() => ({
    default: () => <div>Error loading Topbar component</div>,
  }))
);


const Main = () => {
  return (
    <MainPageSkeleton
      header = {
        <React.Suspense fallback={<Loader text={"Loading Topbar..."}/>}>
          <TopbarComponent/>
        </React.Suspense>
      }
      body = {
        <>
          <Header/>

          <React.Suspense fallback={<Loader text={"Loading Groups..."}/>}>
            <GroupsComponent/>
          </React.Suspense>

          <React.Suspense fallback={<Loader text={"Loading Plot..."}/>}>
            <PlotComponent/>
          </React.Suspense>

          <Form/>

          <React.Suspense fallback={<Loader text={"Loading History..."}/>}>
            <HistoryComponent/>
          </React.Suspense>
        </>
      }
    />
  );
}

export default Main;