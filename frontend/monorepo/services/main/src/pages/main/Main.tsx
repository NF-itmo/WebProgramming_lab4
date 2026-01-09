import "./Main.css";

import Form from "../../modules/plotForm/PlotForm";
import { ColumnLayout as ContentContainer, Loader } from "@packages/shared";
import Header from "../../components/header/Header";
import React from "react";

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

const Main = () => {
  return (
    <ContentContainer>
      
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

    </ContentContainer>
  );
}

export default Main;